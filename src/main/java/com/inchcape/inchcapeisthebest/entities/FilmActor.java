package com.inchcape.inchcapeisthebest.entities;


import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "film_actor")
@IdClass(FilmActorId.class)
public class FilmActor implements Serializable {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_id", referencedColumnName = "actor_id")
    private Actor actor;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id", referencedColumnName = "film_Id")
    private Film film;

    // Constructors, Getters, and Setters
    public FilmActor() {}

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }
}
