package com.nm.api;

import com.nm.dto.CommentRequest;
import com.nm.entity.Comment;
import com.nm.entity.Users;
import com.nm.repository.UserRepository;
import com.nm.service.CommentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentAPI {

	@Autowired
	private CommentService commentService;
	
	@Autowired
	private UserRepository userRepo;

	// ➤ Lấy bình luận theo mangaId
	@GetMapping("/manga/{mangaId}")
	public List<Comment> getCommentsByManga(@PathVariable String mangaId) {
		return commentService.getCommentsByManga(mangaId);
	}

	// ➤ Lấy bình luận theo chapterId
	@GetMapping("/chapter/{chapterId}")
	public List<Comment> getCommentsByChapter(@PathVariable String chapterId) {
		return commentService.getCommentsByChapter(chapterId);
	}

	@PostMapping
	public ResponseEntity<?> addComment(Principal principal, @RequestBody CommentRequest request) {
	    String username = principal.getName(); // 👈 Lấy username
	    Users user = userRepo.findByUsername(username);
	    if (user == null) {
	        throw new RuntimeException("Không tìm thấy người dùng");
	    }

	    Comment created = commentService.addComment(
	        user.getId(),
	        request.getMangaId(),
	        request.getChapterId(),
	        request.getContent()
	    );

	    return ResponseEntity.ok(created);
	}


	// ➤ Xoá bình luận
	@DeleteMapping("/{id}")
	public void deleteComment(@PathVariable Long id, HttpSession session) {
		Users user = (Users) session.getAttribute("loggedInUser");
		if (user == null) {
			throw new RuntimeException("Bạn cần đăng nhập.");
		}
		// (Có thể thêm kiểm tra user là chủ comment nếu muốn)
		commentService.deleteComment(id);
	}
}
