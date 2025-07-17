package com.nm.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nm.entity.Users;
import com.nm.entity.UserFavorite;
import com.nm.repository.UserRepository;
import com.nm.service.UserFavoriteService;

@Controller
@RequestMapping("/favorites")
public class UserFavoriteController {

    @Autowired
    private UserFavoriteService favoriteService;

    @Autowired
    private UserRepository userRepository;

    private Users getCurrentUser(Principal principal) {
        return userRepository.findByUsername(principal.getName());
    }
    
    @GetMapping
    public String viewFavorites(Model model, Principal principal) {
        Users user = getCurrentUser(principal);
        List<UserFavorite> favorites = favoriteService.getFavoritesByUser(user);
        model.addAttribute("favorites", favorites);
        return "favorites"; // view hiển thị danh sách yêu thích
    }

    @PostMapping("/add")
    public String addFavorite(@RequestParam("id") String id, Principal principal) {
        Users user = getCurrentUser(principal);
        favoriteService.addToFavorites(user, id);
        return "redirect:/manga-detail?id=" + id;
    }

    @PostMapping("/remove")
    public String removeFavorite(@RequestParam("id") String id, Principal principal) {
        Users user = getCurrentUser(principal);
        favoriteService.removeFromFavorites(user, id);
        return "redirect:/manga-detail?id=" + id;
    }


   
}