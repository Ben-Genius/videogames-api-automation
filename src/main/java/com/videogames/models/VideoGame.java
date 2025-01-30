package com.videogames.models;

import lombok.Builder;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@Builder
public class VideoGame {
    private Integer id;
    private String name;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String releaseDate;
    
    private Integer reviewScore;
    private String category;
    private String rating;
}