package com.bloghub.service;

import com.bloghub.repository.AuthorRepository;
import com.bloghub.repository.CategoryRepository;
import com.bloghub.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private PostRepository postRepository;
    private CategoryRepository categoryRepository;
    private AuthorRepository authorRepository;

    @Autowired
    public PostService(PostRepository postRepository, CategoryRepository categoryRepository, AuthorRepository authorRepository) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
        this.authorRepository = authorRepository;
    }


}
