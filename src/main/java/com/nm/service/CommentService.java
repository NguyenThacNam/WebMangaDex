package com.nm.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nm.entity.Comment;
import com.nm.entity.Users;
import com.nm.repository.CommentRepository;
import com.nm.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepo;

    @Autowired
    private UserRepository userRepo;

    public Comment addComment(Long userId, String mangaId, String chapterId, String content) {
        Optional<Users> userOpt = userRepo.findById(userId);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User không tồn tại");
        }

        if ((mangaId == null && chapterId == null) || (mangaId != null && chapterId != null)) {
            throw new RuntimeException("Chỉ được bình luận 1 trong 2: manga hoặc chapter");
        }

        Comment comment = new Comment();
        comment.setMangaId(mangaId);
        comment.setChapterId(chapterId);
        comment.setUser(userOpt.get());
        comment.setContent(content);
        comment.setCreatedAt(LocalDateTime.now());  // ✅ Set thời gian tạo

        return commentRepo.save(comment);

       
    }

    public List<Comment> getCommentsByManga(String mangaId) {
        return commentRepo.findByMangaId(mangaId);
    }

    public List<Comment> getCommentsByChapter(String chapterId) {
        return commentRepo.findByChapterId(chapterId);
    }

    public void deleteComment(Long id) {
        commentRepo.deleteById(id);
    }
}

