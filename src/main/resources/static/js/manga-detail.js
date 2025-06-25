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