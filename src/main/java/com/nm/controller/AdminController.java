package com.nm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nm.entity.Users;
import com.nm.repository.UserRepository;
import com.nm.service.UserService;

@RequestMapping("admin")
@Controller
public class AdminController {
    
	@Autowired
	private UserService userService;

	
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
	@GetMapping("/users")
	public String allUsers(Model model) {
	    model.addAttribute("users", userService.getAllUsers());
	    return "admin/users"; // View: users.html
	}
	@PostMapping("/lock-user")
	public String lockUser(@RequestParam("id") Long id, @RequestParam("hours") int hours) {
	    userService.lockUser(id, hours);
	    return "redirect:/admin/users";
	}
	
	@PostMapping("/unlock-user")
	public String unlockUser(@RequestParam("id") Long id) {
	    userService.unlockUser(id);
	    return "redirect:/admin/users";
	}



}
