package com.bloghub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PostResponseDto {

    private Long id;
    private String title;
    private String content;
    private String categoryName;
    private Long categoryId;
    private String authorName;
    private Long authorId;
    private LocalDateTime createdAt;

}
