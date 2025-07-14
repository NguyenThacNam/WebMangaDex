package com.nm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.nm.entity.Users;
import com.nm.repository.UserRepository;

@Controller
public class AuthController {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@GetMapping("/login")
	public String Login() {
		return "login";
	}

	@GetMapping("/register")
	public String Register(Model model) {
		model.addAttribute("users", new Users());
		return "register";
	}

	@PostMapping("/register")
	public String RegisterForm(@ModelAttribute("users") Users users, Model model) {
		if (userRepo.findByUsername(users.getUsername()) != null) {
			model.addAttribute("error", "Tên đăng nhập đã tồn tại!");
			return "register";
		}
		users.setPassword(passwordEncoder.encode(users.getPassword()));
		users.setRole("ROLE_USER"); 
		userRepo.save(users);

		model.addAttribute("success", "Đăng ký thành công!");
		return "login";

	}
}
