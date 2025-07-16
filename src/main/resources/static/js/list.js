const container = document.getElementById("manga-container");
const prevPageBtn = document.getElementById("prev-page");
const nextPageBtn = document.getElementById("next-page");
const pageNumberSpan = document.getElementById("page-number");

let currentPage = 1;
const limit = 12;

// Hàm tải danh sách manga
function loadManga(page) {
	console.log(`Đang tải trang ${page}, offset: ${(page - 1) * limit}`);
	container.innerHTML = "<p>Đang tải dữ liệu...</p>";
	pageNumberSpan.textContent = page;

	const url = `https://api.mangadex.org/manga?limit=${limit}&offset=${(page - 1) * limit}&includes[]=cover_art`;
	console.log("Gửi yêu cầu API:", url); // Debug URL

	fetch(url)
		.then(res => {
			if (!res.ok) {
				throw new Error(`Lỗi API: ${res.status} ${res.statusText}`);
			}
			return res.json();
		})
		.then(data => {
			console.log("Dữ liệu từ API:", data); // Debug dữ liệu API
			if (!data.data || data.data.length === 0) {
				console.log("Không có dữ liệu manga để hiển thị.");
				container.innerHTML = "<p>Không còn truyện để hiển thị.</p>";
				nextPageBtn.disabled = true;
				return;
			}

			nextPageBtn.disabled = false;
			prevPageBtn.disabled = page === 1;
			container.innerHTML = ""; // Xóa nội dung "Đang tải..."

			data.data.forEach(manga => {
				const title = manga.attributes.title?.en || "Không rõ tiêu đề";
				const mangaId = manga.id;

				// Lấy ảnh bìa từ relationships
				const cover = manga.relationships?.find(rel => rel.type === "cover_art");
				const fileName = cover?.attributes?.fileName;

				const coverUrl = fileName
					? `https://uploads.mangadex.org/covers/${mangaId}/${fileName}.256.jpg`
					: "https://via.placeholder.com/256x350?text=No+Cover";

				console.log(`Thêm manga: ${title}, URL bìa: ${coverUrl}`); // Debug mỗi manga

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
		})
		.catch(err => {
			console.error("Lỗi khi tải danh sách manga:", err);
			container.innerHTML = `<p>Lỗi: ${err.message}. Vui lòng thử lại sau.</p>`;
		});
}

// Tải trang đầu tiên
console.log("Tải trang đầu tiên");
loadManga(currentPage);

// Sự kiện nút "Trang trước"
prevPageBtn.addEventListener("click", () => {
	console.log("Nhấn Trang trước, currentPage:", currentPage);
	if (currentPage > 1) {
		currentPage--;
		loadManga(currentPage);
	}
});

// Sự kiện nút "Trang sau"
nextPageBtn.addEventListener("click", () => {
	console.log("Nhấn Trang sau, currentPage:", currentPage + 1);
	currentPage++;
	loadManga(currentPage);
});