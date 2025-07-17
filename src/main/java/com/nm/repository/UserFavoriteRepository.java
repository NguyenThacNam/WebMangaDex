package com.nm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nm.entity.UserFavorite;
import com.nm.entity.Users;

public interface UserFavoriteRepository extends JpaRepository<UserFavorite, Long> {

    boolean existsByUserAndMangaId(Users user, String mangaId);

    Optional<UserFavorite> findByUserAndMangaId(Users user, String mangaId);

    List<UserFavorite> findByUser(Users user);

    void deleteByUserAndMangaId(Users user, String mangaId);
}
