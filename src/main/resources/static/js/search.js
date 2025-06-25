const keyword = new URLSearchParams(window.location.search).get('keyword');
if (keyword) {
	const apiUrl = `https://api.mangadex.org/manga?title=${encodeURIComponent(keyword)}&limit=20&includes[]=cover_art&availableTranslatedLanguage[]=en&hasAvailableChapters=true`;

	fetch(apiUrl)
		.then(res => res.json())
		.then(data => {
			const resultsDiv = document.getElementById('manga-results');
			resultsDiv.innerHTML = '';
			const mangas = data.data;

			if (!mangas || mangas.length === 0) {
				resultsDiv.innerHTML = '<p class="text-muted">Không tìm thấy truyện nào.</p>';
				return;
			}

			mangas.forEach(manga => {
				const id = manga.id;
				const title = manga.attributes.title.en || 'Không có tiêu đề';
				const coverRel = manga.relationships.find(r => r.type === 'cover_art');
				const cover = coverRel?.attributes?.fileName;
				const imageUrl = cover
					? `https://uploads.mangadex.org/covers/${id}/${cover}.256.jpg`
					: 'https://via.placeholder.com/256x360?text=No+Image';

				const card = `
                    <div class="col">
                        <div class="card h-100">
                            <img src="${imageUrl}" class="card-img-top" alt="${title}">
                            <div class="card-body">
                                <h5 class="card-title">${title}</h5>
								<a href="/manga-detail?id=${id}" class="btn btn-read">
								    <i class="fas fa-book-open me-1"></i> Đọc truyện
								</a>

                            </div>
                        </div>
                    </div>`;
				resultsDiv.innerHTML += card;
			});
		})
		.catch(err => {
			console.error("Lỗi API:", err);
			document.getElementById('manga-results').innerHTML = '<p class="text-danger">Không thể tải dữ liệu từ API.</p>';
		});
}
