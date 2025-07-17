package com.nm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nm.entity.UserFavorite;
import com.nm.entity.Users;
import com.nm.repository.UserFavoriteRepository;

import jakarta.transaction.Transactional;

@Service
public class UserFavoriteService {

    @Autowired
    private UserFavoriteRepository favoriteRepo;

    public void addToFavorites(Users user, String mangaId) {
        favoriteRepo.findByUserAndMangaId(user, mangaId).orElseGet(() -> {
            UserFavorite favorite = new UserFavorite();
            favorite.setUser(user);
            favorite.setMangaId(mangaId);
            return favoriteRepo.save(favorite);
        });
    }
    
    
    @Transactional
    public void removeFromFavorites(Users user, String mangaId) {
        favoriteRepo.deleteByUserAndMangaId(user, mangaId);
    }

    public List<UserFavorite> getFavoritesByUser(Users user) {
        return favoriteRepo.findByUser(user);
    }

    public boolean isFavorited(Users user, String mangaId) {
        return favoriteRepo.findByUserAndMangaId(user, mangaId).isPresent();
    }
}