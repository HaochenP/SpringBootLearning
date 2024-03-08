package com.inchcape.inchcapeisthebest.DTO;

import lombok.Data;

@Data
public class FilmDTO {
    private String title;
    private String description;
    private Integer releaseYear;

    public FilmDTO() {
    }

    public FilmDTO(String title, String description, Integer releaseYear) {
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
    }
}
