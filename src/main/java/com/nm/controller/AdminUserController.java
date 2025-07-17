package com.nm.controller;

import com.nm.service.UserService;
import com.nm.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminUserController {

    @Autowired
    private UserService userService;

    // Hiển thị danh sách người dùng bị khóa
    @GetMapping("/admin/locked-users")
    public String lockedUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers().stream()
                .filter(Users::isLocked)
                .toList());
        return "admin/locked-users"; // View: locked-users.html
    }

    // Mở khóa tài khoản
    @PostMapping("/admin/unlock-user/{id}")
    public String unlockUser(@PathVariable Long id) {
        userService.unlockUser(id);
        return "redirect:/admin/locked-users";
    }
}
