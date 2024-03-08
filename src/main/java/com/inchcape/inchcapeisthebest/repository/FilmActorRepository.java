package com.inchcape.inchcapeisthebest.repository;


import com.inchcape.inchcapeisthebest.entities.FilmActor;
import com.inchcape.inchcapeisthebest.entities.FilmActorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmActorRepository extends JpaRepository<FilmActor, FilmActorId> {
    List<FilmActor> findByActorId(Long actorId);
}
