window.onload = function() {
	const list = document.getElementById('manga-list');
	list.innerHTML = "<p>Đang tải truyện...</p>";

	const fetchManga = (lang) => {
		const url = `https://api.mangadex.org/manga?limit=20&order[updatedAt]=desc&includes[]=cover_art${lang ? `&translatedLanguage[]=${lang}` : ''}`;
		return fetch(url).then(res => res.json());
	};

	// 
	fetchManga('vi')
		.then(data => {
			if (data.data?.length > 0) return data;
			return fetchManga('en');
		})
		.then(data => {
			if (data.data?.length > 0) return data;
			return fetchManga(null); 
		})
		.then(data => {
			list.innerHTML = "";
			if (!data || !data.data || data.data.length === 0) {
				list.innerHTML = "<p class='text-muted'>Không tìm thấy truyện nào 😥</p>";
				return;
			}

			data.data.forEach(manga => {
				const title = manga.attributes.title.en || Object.values(manga.attributes.title)[0] || "Không có tên";
				const mangaId = manga.id;

				const cover = manga.relationships.find(r => r.type === "cover_art");
				const fileName = cover?.attributes?.fileName;
				const imageUrl = fileName
					? `https://uploads.mangadex.org/covers/${mangaId}/${fileName}.256.jpg`
					: 'https://via.placeholder.com/150x220?text=No+Image';

				const card = `
                    <div class="col-md-3 mb-4">
                        <div class="card h-100">
                            <img src="${imageUrl}" class="card-img-top" alt="${title}">
                            <div class="card-body">
                                <h6 class="card-title">${title}</h6>
                                <a href="/manga-detail?id=${mangaId}" class="btn btn-read">
                                    <i class="fas fa-book-open me-1"></i> Đọc truyện
                                </a>
								
                            </div>
                        </div>
                    </div>`;
				list.innerHTML += card;
			});
		})
		.catch(error => {
			console.error('Error fetching manga:', error);
			list.innerHTML = `
                <div class="col-12 text-center py-5">
                    <i class="fas fa-exclamation-triangle fa-3x text-warning mb-3"></i>
                    <h4>Không thể tải danh sách truyện</h4>
                    <p>Vui lòng thử lại sau hoặc kiểm tra kết nối mạng của bạn.</p>
                    <button class="btn btn-primary" onclick="window.location.reload()">
                        <i class="fas fa-sync-alt me-1"></i> Tải lại
                    </button>
                </div>`;
		});
};