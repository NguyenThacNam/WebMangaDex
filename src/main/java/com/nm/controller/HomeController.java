package com.nm.controller;

import java.security.Principal;
import com.nm.service.NotificationService;
import com.nm.service.UserFavoriteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nm.entity.Users;
import com.nm.repository.UserRepository;

@Controller
public class HomeController {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private UserFavoriteService favoriteService;

	@GetMapping("")
	public String Home() {
		return "home";
	}

	@GetMapping("/manga-detail")
	public String showMangaDetailPage(@RequestParam String id, Model model, Principal principal) {
		model.addAttribute("mangaId", id);

		if (principal != null) {
			Users user = userRepo.findByUsername(principal.getName());
			boolean isFavorited = favoriteService.isFavorited(user, id);
			model.addAttribute("isFavorited", isFavorited);
		} else {
			model.addAttribute("isFavorited", false);
		}

		return "manga-detail";
	}

	@GetMapping("/read-chapter.html")
	public String readChapter() {
		return "read-chapter";
	}

	@GetMapping("/manga-list")
	public String showMangaListPage() {
		return "manga-list";
	}

	@GetMapping("/search")
	public String searchPage(@RequestParam(required = false) String keyword, Model model) {
		model.addAttribute("keyword", keyword != null ? keyword : "");
		return "search";
	}

	@GetMapping("/profile")
	public String profilePage(Model model, Principal principal) {
		String username = principal.getName(); // Lấy username từ session hiện tại
		Users user = userRepo.findByUsername(username);
		model.addAttribute("user", user);

		// Thêm danh sách thông báo
		model.addAttribute("notifications", notificationService.getNotificationsByUser(user));
		model.addAttribute("unreadCount", notificationService.countUnread(user));

		return "profile"; // file profile.html
	}

	@GetMapping("/manga-category")
    public String showMangaCategoryPage(@RequestParam("genre") String genre, Model model) {
        model.addAttribute("genre", genre); // Truyền genre vào Thymeleaf nếu cần
        return "manga-category"; // Trả về trang HTML
    }
}
