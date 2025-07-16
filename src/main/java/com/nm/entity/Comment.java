package com.nm.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ✅ Tự động tăng
    private Long id;

    private String mangaId;
    private String chapterId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createdAt = LocalDateTime.now();

	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public Comment(String mangaId, String chapterId, Users user, String content, LocalDateTime createdAt) {
	    this.mangaId = mangaId;
	    this.chapterId = chapterId;
	    this.user = user;
	    this.content = content;
	    this.createdAt = createdAt;
	}




	public  Long getId() {
		return id;
	}

	public  void setId(Long id) {
		this.id = id;
	}

	public  String getMangaId() {
		return mangaId;
	}

	public  void setMangaId(String mangaId) {
		this.mangaId = mangaId;
	}

	public  String getChapterId() {
		return chapterId;
	}

	public  void setChapterId(String chapterId) {
		this.chapterId = chapterId;
	}

	public  Users getUser() {
		return user;
	}

	public  void setUser(Users user) {
		this.user = user;
	}

	public  String getContent() {
		return content;
	}

	public  void setContent(String content) {
		this.content = content;
	}

	public  LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public  void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	
    
}

