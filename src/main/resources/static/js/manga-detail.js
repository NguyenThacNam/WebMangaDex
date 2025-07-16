if (!mangaId) {
     alert("Thiếu ID truyện!");
   }

   // 🔹 Lấy chi tiết manga
   fetch(`https://api.mangadex.org/manga/${mangaId}?includes[]=author&includes[]=cover_art`)
     .then(res => res.json())
     .then(data => {
       const manga = data.data;
       const attr = manga.attributes;

       // Tiêu đề
       document.getElementById("title").textContent = attr.title?.en || "Không có tiêu đề";

       // Mô tả
       document.getElementById("description").textContent = attr.description?.en || "Không có mô tả";

       // Tác giả
       const authorRel = manga.relationships?.find(r => r.type === "author");
       const authorName = authorRel?.attributes?.name || "Không rõ";
       document.getElementById("author").textContent = authorName;

       // Ảnh bìa
       const coverRel = manga.relationships?.find(r => r.type === "cover_art");
       const fileName = coverRel?.attributes?.fileName;
       if (fileName) {
         const coverUrl = `https://uploads.mangadex.org/covers/${manga.id}/${fileName}`;
         const img = document.getElementById("cover");
         img.src = coverUrl;
         img.style.display = "block";
       }
     })
     .catch(err => {
       console.error("Lỗi khi lấy chi tiết truyện:", err);
     });

   // 🔹 Lấy danh sách chương
   fetch(`https://api.mangadex.org/chapter?manga=${mangaId}&translatedLanguage[]=en&limit=20&order[chapter]=asc`)
     .then(res => res.json())
     .then(data => {
       const list = document.getElementById("chapter-list");
       data.data.forEach(chap => {
         const chapNum = chap.attributes.chapter || "?";
         const chapId = chap.id;
         const li = document.createElement("li");
         li.innerHTML = `<a href="/read-chapter.html?id=${chapId}">Chương ${chapNum}</a>`;
         list.appendChild(li);
       });
     })
     .catch(err => {
       console.error("Lỗi khi lấy danh sách chương:", err);
     });
	 document.addEventListener("DOMContentLoaded", () => {
	     loadComments();

	     const form = document.getElementById("comment-form");
	     form?.addEventListener("submit", function (e) {
	         e.preventDefault();
	         const content = document.getElementById("comment-content").value.trim();
	         if (!content) return;

	         fetch('/api/comments', {
	             method: 'POST',
	             headers: {
	                 'Content-Type': 'application/json'
	             },
				 credentials: 'same-origin',
	             body: JSON.stringify({
	                 mangaId: mangaId,
	                 content: content
	             })
	         })
	         .then(res => {
	             if (!res.ok) throw new Error("Bạn cần đăng nhập để bình luận.");
	             return res.json();
	         })
	         .then(() => {
	             document.getElementById("comment-content").value = '';
	             loadComments();
	         })
	         .catch(err => alert(err.message));
	     });
	 });

	 function loadComments() {
	     fetch(`/api/comments/manga/${mangaId}`)
	         .then(res => res.json())
	         .then(comments => {
	             const section = document.getElementById("comment-section");
	             const noComment = document.getElementById("no-comment");
	             section.innerHTML = '';

	             if (comments.length === 0) {
	                 noComment.style.display = 'block';
	             } else {
	                 noComment.style.display = 'none';
	                 comments.forEach(c => {
	                     const div = document.createElement('div');
	                     div.className = 'mb-2 border-bottom pb-2';
	                     div.innerHTML = `
	                         <strong>${c.user.username}</strong>: ${c.content}
	                         <br>
	                         <small>${new Date(c.createdAt).toLocaleString()}</small>
	                     `;
	                     section.appendChild(div);
	                 });
	             }
	         });
	 }
