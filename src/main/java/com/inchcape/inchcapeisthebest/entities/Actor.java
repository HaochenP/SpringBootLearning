package com.inchcape.inchcapeisthebest.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "actor")
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "actor_id")
    private Long id;
    @Column(name = "first_name")
    private String firstname;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "last_update")
    private LocalDateTime lastUpdate;
    @Column(name = "film_id")
    @ManyToMany
    @JoinTable(
            name = "film_actor",
            joinColumns = {@JoinColumn(name = "film_id")},
            inverseJoinColumns = {@JoinColumn(name = "actor_id")}
    )
    @JsonIgnore
    @Setter(AccessLevel.NONE)
    private List<Film> films = new ArrayList<>();
    public Actor() {
    }

    public Actor(Long id, String firstname, String lastName, LocalDateTime lastUpdate, ArrayList<Film> films) {
        this.id = id;
        this.firstname = firstname;
        this.lastName = lastName;
        this.lastUpdate = lastUpdate;
        this.films = films;
    }


    @PrePersist
    @PreUpdate
    private void updateTimestamp() {
        lastUpdate = LocalDateTime.now();
    }


    @Override
    public String toString() {
        return "Actor{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastName='" + lastName + '\'' +
                ", lastUpdate=" + lastUpdate +
                '}';
    }

}
