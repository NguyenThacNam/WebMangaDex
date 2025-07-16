document.addEventListener("DOMContentLoaded", () => {
    loadChapterComments();

    const form = document.getElementById("chapter-comment-form");
    form?.addEventListener("submit", function (e) {
        e.preventDefault();
        const content = document.getElementById("chapter-comment-content").value.trim();
        if (!content) return;

        fetch('/api/comments', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
			credentials: 'same-origin',
            body: JSON.stringify({
                chapterId: chapterId,
                content: content
            })
        })
        .then(res => {
            if (!res.ok) throw new Error("Bạn cần đăng nhập để bình luận.");
            return res.json();
        })
        .then(() => {
            document.getElementById("chapter-comment-content").value = '';
            loadChapterComments();
        })
        .catch(err => alert(err.message));
    });
});

function loadChapterComments() {
    fetch(`/api/comments/chapter/${chapterId}`)
        .then(res => res.json())
        .then(comments => {
            const section = document.getElementById("chapter-comment-section");
            const noComment = document.getElementById("chapter-no-comment");
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
