package com.nm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nm.entity.Users;
import com.nm.repository.UserRepository;

@RequestMapping("admin")
@Controller
public class AdminController {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@GetMapping("")
	public String Admin() {
		return "admin/dashboard";
	}
	
	@GetMapping("/login")
    public String showLoginForm() {
        return "admin/login"; // trỏ tới templates/admin/login.html
    }

	@GetMapping("/register")
	public String Register(Model model) {
		model.addAttribute("admin", new Users());
		return "admin/register";
	}

	@PostMapping("/register")
	public String RegisterForm(@ModelAttribute("admin") Users users) {
		users.setPassword(passwordEncoder.encode(users.getPassword()));
		users.setRole("ROLE_ADMIN");
		userRepo.save(users);
		return "redirect:/admin/login";
	}
}
