package com.nm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    
	@GetMapping("")
	public String Home() {
		return "home";
	}
	  @GetMapping("/manga-detail")
	    public String showMangaDetailPage(@RequestParam String id, Model model) {
	        model.addAttribute("mangaId", id);
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



	
}
