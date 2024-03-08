package com.inchcape.inchcapeisthebest.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inchcape.inchcapeisthebest.entities.Film;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
public class ActorDTO {
    private Long id;
    @Size(min = 1, max = 45)
    private String firstname;

    @Size(min = 1, max = 45)
    private String lastName;

    private ArrayList<Long> FilmIds;


    private List<Film> films = new ArrayList<>();
    public ActorDTO() {
    }

    public ActorDTO(Long id, String firstname, String lastName, ArrayList<Long> filmIds, ArrayList<Film> films) {
        this.firstname = firstname;
        this.lastName = lastName;
        this.FilmIds = filmIds;
        this.films = films;
        this.id = id;
    }

}
