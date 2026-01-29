package com.bloghub.controller;

import com.bloghub.dto.PostRequestDto;
import com.bloghub.service.PostService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController// Marks this class as a REST controller
@RequestMapping("/api/posts")// Base URL for Post-related endpoints
public class PostController {

    private PostService postService;  // Inject PostService

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    public ResponseEntity<?> createPost(@RequestBody @Valid PostRequestDto postRequestDto, HttpSession session) {
        
        // Implementation for creating a post
        return ResponseEntity.ok().build();
    }
