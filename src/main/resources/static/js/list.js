const container = document.getElementById("manga-container");

fetch("https://api.mangadex.org/manga?limit=12&offset=0")
  .then(res => res.json())
  .then(data => {
    data.data.forEach(manga => {
      const title = manga.attributes.title?.en || "Không rõ tiêu đề";
      const mangaId = manga.id;

      //image
      fetch(`https://api.mangadex.org/cover?manga[]=${mangaId}`)
        .then(res => res.json())
        .then(coverData => {
          const cover = coverData.data[0];
          const fileName = cover?.attributes?.fileName;

          const coverUrl = fileName
            ? `https://uploads.mangadex.org/covers/${mangaId}/${fileName}.256.jpg`
            : "https://via.placeholder.com/256x350?text=No+Cover";

          // view
          const col = document.createElement("div");
          col.className = "col-md-3 mb-4";
          col.innerHTML = `
            <div class="card h-100">
              <img src="${coverUrl}" class="card-img-top" alt="${title}" onerror="this.src='https://via.placeholder.com/256x350?text=No+Image';">
              <div class="card-body">
                <h5 class="card-title">${title}</h5>
				<a href="/manga-detail?id=${mangaId}" class="btn btn-read">
				                                   <i class="fas fa-book-open me-1"></i> Đọc truyện
				                               </a>
              </div>
            </div>
          `;
          container.appendChild(col);
        });
    });
  })
  .catch(err => console.error("Lỗi khi tải danh sách manga:", err));