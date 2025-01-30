package com.videogames.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@Builder
@NoArgsConstructor  // Add this annotation
@AllArgsConstructor // Add this if not already present
public class VideoGame {
    private Integer id;
    private String name;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String releaseDate;
    
    private Integer reviewScore;
    private String category;
    private String rating;
}