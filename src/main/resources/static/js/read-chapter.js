const urlParams = new URLSearchParams(window.location.search);
  const chapterId = urlParams.get("id");

  if (!chapterId) {
    alert("Thiếu chapter ID!");
    throw new Error("Thiếu chapter ID");
  }

  let mangaId = "";
  let chapterNumber = "";

  
  fetch(`https://api.mangadex.org/chapter/${chapterId}`)
    .then(res => res.json())
    .then(data => {
      const attr = data.data.attributes;
      chapterNumber = attr.chapter || "?";
      const chapterTitle = attr.title || "";
      document.getElementById("chapter-title").textContent =
        `Chương ${chapterNumber}` + (chapterTitle ? `: ${chapterTitle}` : "");

      //  mangaId
      const rel = data.data.relationships.find(r => r.type === "manga");
      mangaId = rel?.id;

      //  API lấy tên truyện
      return fetch(`https://api.mangadex.org/manga/${mangaId}`);
    })
    .then(res => res.json())
    .then(mangaData => {
      const title = mangaData.data.attributes.title?.en || "Không rõ tên truyện";
      document.getElementById("manga-title").textContent = title;

      // điều hướng
      return fetch(`https://api.mangadex.org/chapter?manga=${mangaId}&translatedLanguage[]=en&order[chapter]=asc&limit=100`);
    })
    .then(res => res.json())
    .then(chapterList => {
      const chapters = chapterList.data
        .filter(ch => !isNaN(parseFloat(ch.attributes.chapter)))
        .sort((a, b) => parseFloat(a.attributes.chapter) - parseFloat(b.attributes.chapter));

      const currentIndex = chapters.findIndex(ch => ch.id === chapterId);
      const prev = chapters[currentIndex - 1];
      const next = chapters[currentIndex + 1];

      if (prev) {
        const btn = document.getElementById("prev-btn");
        btn.href = `/read-chapter.html?id=${prev.id}`;
        btn.textContent = `← Chương ${prev.attributes.chapter}`;
        btn.style.display = "inline-block";
      }
      if (next) {
        const btn = document.getElementById("next-btn");
        btn.href = `/read-chapter.html?id=${next.id}`;
        btn.textContent = `Chương ${next.attributes.chapter} →`;
        btn.style.display = "inline-block";
      }

      //  API ảnh chương
      return fetch(`https://api.mangadex.org/at-home/server/${chapterId}`);
    })
    .then(res => res.json())
    .then(data => {
      const baseUrl = data.baseUrl;
      const hash = data.chapter.hash;
      const pages = data.chapter.data;

      const container = document.getElementById("image-container");
      pages.forEach((filename, index) => {
        const img = document.createElement("img");
        img.src = `${baseUrl}/data/${hash}/${filename}`;
        img.alt = `Trang ${index + 1}`;
        img.className = "page-img";
        container.appendChild(img);
      });
    })
    .catch(err => {
      console.error("Lỗi:", err);
      document.getElementById("chapter-title").textContent = "Không thể tải chương.";
    });