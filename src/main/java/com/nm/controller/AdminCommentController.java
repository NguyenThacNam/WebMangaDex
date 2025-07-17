package com.nm.controller;

import com.nm.service.CommentService;
import com.nm.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AdminCommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/admin/comments")
    public String viewComments(@RequestParam(defaultValue = "0") int page, Model model) {
        int pageSize = 10;
        Page<Comment> commentPage = commentService.getPagedComments(page, pageSize);

        model.addAttribute("commentPage", commentPage);
        model.addAttribute("currentPage", page);
        return "admin/comments";
    }
    @PostMapping("/admin/comments/delete/{id}")
    public String deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return "redirect:/admin/comments"; // Quay lại trang danh sách sau khi xóa
    }
}
