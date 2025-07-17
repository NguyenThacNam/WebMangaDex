package com.nm.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "user_favorites", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "manga_id"}))
public class UserFavorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column(name = "manga_id", nullable = false)
    private String mangaId;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    
    
	public UserFavorite() {
		super();
		// TODO Auto-generated constructor stub
	}



	public UserFavorite(Long id, Users user, String mangaId, LocalDateTime createdAt) {
		super();
		this.id = id;
		this.user = user;
		this.mangaId = mangaId;
		this.createdAt = createdAt;
	}



	public  Long getId() {
		return id;
	}



	public  void setId(Long id) {
		this.id = id;
	}



	public  Users getUser() {
		return user;
	}



	public  void setUser(Users user) {
		this.user = user;
	}



	public  String getMangaId() {
		return mangaId;
	}



	public  void setMangaId(String mangaId) {
		this.mangaId = mangaId;
	}



	public  LocalDateTime getCreatedAt() {
		return createdAt;
	}



	public  void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

    // Getter, Setter
	
    
}

