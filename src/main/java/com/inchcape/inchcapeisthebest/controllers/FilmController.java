package com.inchcape.inchcapeisthebest.controllers;

import com.inchcape.inchcapeisthebest.entities.Film;
import com.inchcape.inchcapeisthebest.service.FilmService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FilmController {
    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("allFilms")
    public ResponseEntity<?> getAllFilms() {
        try{
            List<Film> films = filmService.getFilms();
            return ResponseEntity.ok(films);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @GetMapping("filmById/{id}")
    public ResponseEntity<?> getFilmById(@PathVariable Long id) {
        try{
            Film film = filmService.getFilmById(id);
            if (film == null) {
                return ResponseEntity.badRequest().body("Film not found");
            }
            return ResponseEntity.ok(film);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error has occurred while fetching film" + e.getMessage());
        }
    }

    @PostMapping("saveFilm")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> saveFilm(@RequestBody Film film) {
        try{
            Film savedFilm = filmService.saveFilm(film);
            return ResponseEntity.ok(savedFilm);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error has occurred while saving film" + e.getMessage());
        }
    }




    @DeleteMapping("deleteFilm/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?>  deleteFilm(@PathVariable Long id) {
        try{
            filmService.deleteFilm(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @PutMapping("updateFilm")
    public Film updateFilm(Film film) {
        return filmService.updateFilm(film);
    }

    @GetMapping("actorsIn/{id}")
    public ResponseEntity<?> actorsIn(@PathVariable Long id) {
        final var film = filmService.getFilmById(id);
        return ResponseEntity.ok(film.getActors());
    }

}
