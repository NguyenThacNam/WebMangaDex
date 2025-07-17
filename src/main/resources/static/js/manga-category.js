// 📌 DOM Elements
const genreTitle = document.getElementById('genreTitle');
const mangaList = document.getElementById('mangaList');

// 📌 Đọc genre từ URL
const urlParams = new URLSearchParams(window.location.search);
const genreParam = urlParams.get('genre');

// 📌 Map tên thể loại sang ID của MangaDex
const genreMap = {
    action: '391b0423-d847-456f-aff0-8b0cfcf2f4e6',
    adventure: '87cc87cd-a395-47af-b27a-93258283bbc6',
    comedy: '4d32cc48-9f00-4cca-9b5a-a839f0764984',
    drama: 'f8f62932-27da-4fe4-8ee1-6779a8c5edba',
    fantasy: 'cdc58593-87dd-415e-bbc0-6d3a66d541f1'
    // Thêm thể loại khác nếu cần
};

const genreId = genreMap[genreParam];

// 📌 Kiểm tra thể loại hợp lệ
if (!genreId) {
    genreTitle.textContent = 'Thể loại không hợp lệ';
    mangaList.innerHTML = '<p>Không tìm thấy thể loại này.</p>';
} else {
    genreTitle.textContent = `Thể loại: ${genreParam}`;
    loadMangaByGenre(genreId);
}

// 📘 Gọi API MangaDex để lấy manga theo thể loại
async function loadMangaByGenre(tagId) {
    try {
        const url = `https://api.mangadex.org/manga?includedTags[]=${tagId}&limit=12&contentRating[]=safe&includes[]=cover_art`;
        console.log('Gọi API:', url);
        const res = await fetch(url);
        const data = await res.json();

        if (!data || !data.data || data.data.length === 0) {
            mangaList.innerHTML = '<p>Không tìm thấy truyện nào cho thể loại này.</p>';
            return;
        }

        displayMangaList(data.data);
    } catch (error) {
        console.error('Lỗi khi gọi API:', error);
        mangaList.innerHTML = '<p>Đã xảy ra lỗi khi tải dữ liệu.</p>';
    }
}

// 🎨 Hiển thị danh sách manga
function displayMangaList(mangaArray) {
    mangaList.innerHTML = '';

    mangaArray.forEach(manga => {
        const titleObj = manga.attributes.title;
        const title = titleObj.en || titleObj.ja || titleObj.vi || 'Không có tiêu đề';
        const mangaId = manga.id;

        const coverRel = manga.relationships.find(r => r.type === 'cover_art');
        const coverFileName = coverRel?.attributes?.fileName;
        const imageUrl = (coverFileName && mangaId)
            ? `https://uploads.mangadex.org/covers/${mangaId}/${coverFileName}`
            : 'https://placehold.co/256x350?text=No+Cover';

        const col = document.createElement('div');
        col.className = 'col-md-3 mb-4';
        col.innerHTML = `
            <div class="card h-100">
                <img src="${imageUrl}" class="card-img-top" alt="${title}" onerror="this.src='https://placehold.co/256x350?text=No+Image';">
                <div class="card-body">
                    <h5 class="card-title">${title}</h5>
                    <a href="/manga-detail?id=${mangaId}" class="btn btn-read">
                        <i class="fas fa-book-open me-1"></i> Đọc truyện
                    </a>
                </div>
            </div>
        `;
        mangaList.appendChild(col);
    });
}
