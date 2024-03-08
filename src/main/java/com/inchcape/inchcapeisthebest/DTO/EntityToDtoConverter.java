package com.inchcape.inchcapeisthebest.DTO;

import com.inchcape.inchcapeisthebest.entities.Actor;
import com.inchcape.inchcapeisthebest.entities.Film;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EntityToDtoConverter {
    private final ModelMapper modelMapper = new ModelMapper();

    public FilmDTO convertFilmToDto(Film film) {
        return modelMapper.map(film, FilmDTO.class);
    }

    public Film convertFilmToEntity(FilmDTO filmDTO) {
        return modelMapper.map(filmDTO, Film.class);
    }

    public ActorDTO convertActorToDto(Actor actor) {
        return modelMapper.map(actor, ActorDTO.class);
    }

    public Actor convertActorToEntity(ActorDTO actorDTO) {
        return modelMapper.map(actorDTO, Actor.class);
    }


}
