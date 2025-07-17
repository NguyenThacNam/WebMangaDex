package com.nm.controller;

import com.nm.entity.Users;
import com.nm.service.UserService;
import com.nm.service.ViolationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminViolationController {

    @Autowired
    private ViolationService violationService;

    @Autowired
    private UserService userService;

    // Xem vi phạm của người dùng
    @GetMapping("/admin/violations/{userId}")
    public String viewViolations(@PathVariable Long userId, Model model) {
        Users user = userService.getUserById(userId);
        model.addAttribute("violations", violationService.getViolationsByUser(user));
        model.addAttribute("user", user);
        return "admin/violations"; // View: violations.html
    }
}
