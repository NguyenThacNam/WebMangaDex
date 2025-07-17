package com.nm.controller;

import com.nm.entity.Users;
import com.nm.service.NotificationService;
import com.nm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class NotificationController {
    
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userService;

    // Hiển thị hộp thư
    @GetMapping("/inbox")
    public String inbox(@RequestParam("username") String username, Model model) {
        Users user = userService.getUserByUsername(username);
        model.addAttribute("notifications", notificationService.getNotificationsByUser(user));
        model.addAttribute("unreadCount", notificationService.countUnread(user));
        return "inbox"; // View: inbox.html
    }

    // Đánh dấu đã đọc
    @PostMapping("/inbox/read/{id}")
    public String markAsRead(@PathVariable Long id, @RequestParam("username") String username) {
        notificationService.markAsRead(id);
        return "redirect:/inbox?username=" + username;
    }
}
