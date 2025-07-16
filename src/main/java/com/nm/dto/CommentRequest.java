package com.nm.dto;

public class CommentRequest {
    private String mangaId;
    private String chapterId;
    private String content;
	public CommentRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CommentRequest(String mangaId, String chapterId, String content) {
		super();
		this.mangaId = mangaId;
		this.chapterId = chapterId;
		this.content = content;
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
	public  String getContent() {
		return content;
	}
	public  void setContent(String content) {
		this.content = content;
	}

    
    
}
