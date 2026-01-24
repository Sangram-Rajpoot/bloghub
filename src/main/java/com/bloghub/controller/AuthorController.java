package com.bloghub.controller;

import com.bloghub.dto.AuthorResponseDto;
import com.bloghub.entity.Author;
import com.bloghub.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController// Indicates that this class is a REST controller in the Spring framework
@RequestMapping("/api/users")// Base URL mapping for author-related endpoints
public class AuthorController {

    private final AuthorService authorService;// Service for author-related business logic

    public AuthorController(AuthorService authorService) {// Constructor injection of the AuthorService
        this.authorService = authorService;// Initialize the service
    }

    @GetMapping("{id}")// Maps HTTP GET requests to this method for retrieving an author by ID
    public ResponseEntity<AuthorResponseDto> getAuthorById(@RequestParam Long id) {
        Author author = authorService.getAuthorById(id);// Fetch author details using the service
        AuthorResponseDto authorResponseDto = new AuthorResponseDto(id, author.getName(), author.getEmail(), author.getRole(), author.getAbout());// Create a DTO for the author response
        return ResponseEntity.ok(authorResponseDto);// Return the author details in the response
    }


}
