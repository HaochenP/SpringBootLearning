package com.inchcape.inchcapeisthebest.service;

import com.inchcape.inchcapeisthebest.DTO.ActorDTO;
import com.inchcape.inchcapeisthebest.DTO.EntityToDtoConverter;
import com.inchcape.inchcapeisthebest.entities.Actor;
import com.inchcape.inchcapeisthebest.entities.Film;
import com.inchcape.inchcapeisthebest.entities.FilmActor;
import com.inchcape.inchcapeisthebest.input.ActorInput;
import com.inchcape.inchcapeisthebest.repository.FilmActorRepository;
import com.inchcape.inchcapeisthebest.repository.FilmRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.inchcape.inchcapeisthebest.repository.ActorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ActorService {

    @Autowired
    private final ActorRepository actorRepository;
    private final FilmRepository filmRepository;
    private final EntityToDtoConverter entityToDtoConverter;

    private final FilmActorRepository filmActorRepository;

    public ActorService(ActorRepository actorRepository, FilmRepository filmRepository, EntityToDtoConverter entityToDtoConverter
    , FilmActorRepository filmActorRepository) {
        this.actorRepository = actorRepository;
        this.filmRepository = filmRepository;
        this.entityToDtoConverter = entityToDtoConverter;
        this.filmActorRepository = filmActorRepository;
    }

    public List<ActorDTO> getActors() {
        List<Actor> actors = actorRepository.findAll();
        return actors.stream().map(entityToDtoConverter::convertActorToDto).toList();
    }
    public ActorDTO getActorById(Long id) {
        Actor actor = actorRepository.findById(id).orElse(null);
        if (actor == null) {
            return null;
        }
        return entityToDtoConverter.convertActorToDto(actor);
    }
    public Actor saveActor(Actor actor) {
        return actorRepository.save(actor);
    }
    public void deleteActor(Long id) {
        List<FilmActor> filmActors = filmActorRepository.findByActorId(id);
        filmActorRepository.deleteAll(filmActors);


        actorRepository.deleteById(id);
    }
    public Actor updateActor(Long id, ActorInput actor) {
        Actor actorToUpdate = actorRepository.findById(id).orElse(null);
        if (actorToUpdate == null) {
            return null;
        }
        actorToUpdate.setFirstname(actor.getFirstname());
        actorToUpdate.setLastName(actor.getLastName());
        return actorRepository.save(actorToUpdate);
    }
    @Transactional
    public Actor AddFilmToActor(Long actorId, ActorDTO actorViewModel){
        Actor actor = actorRepository.findById(actorId).orElse(null);
        if (actor == null) {
            throw new RuntimeException("Actor not found");
        }

        for (Long filmId : actorViewModel.getFilmIds()) {
            Film film = filmRepository.findById(filmId).orElseThrow(() -> new EntityNotFoundException("Film not found"));
            actor.getFilms().add(film);
            film.getActors().add(actor);
        }

        return actorRepository.save(actor);

    }

    public void deleteActorsByIds(List<Long> ids) {
        for (Long id : ids) {
            List<FilmActor> filmActors = filmActorRepository.findByActorId(id);
            filmActorRepository.deleteAll(filmActors);
        }
        actorRepository.deleteAllById(ids);
    }

    public Optional<Actor> findLastActor() {
        return actorRepository.findLastEntry();
    }

}
