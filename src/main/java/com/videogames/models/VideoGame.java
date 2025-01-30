// VideoGame.java
package com.videogames.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VideoGame {
    private String name;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String releaseDate;
    
    private Integer reviewScore;
    private String category;
    private String rating;
}