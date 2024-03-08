package com.inchcape.inchcapeisthebest.repository;

import com.inchcape.inchcapeisthebest.entities.Actor;
import jdk.jfr.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ActorRepository extends JpaRepository<Actor, Long> {
    List<Actor> findAll();


    @Query(value = "SELECT * FROM actor WHERE first_name = :firstName LIMIT 1", nativeQuery = true)
    Optional<Actor> findActorByFirstName(@Param("firstName") String firstName);

    @Query(value = "SELECT * FROM actor ORDER BY actor_id DESC LIMIT 1", nativeQuery = true)
    Optional<Actor> findLastEntry();

}
