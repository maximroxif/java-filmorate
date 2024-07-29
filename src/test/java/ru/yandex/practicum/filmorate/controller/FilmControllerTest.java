package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FilmControllerTest {
    FilmController filmController;
    Film film;

    @BeforeEach
    void Start() {
        filmController = new FilmController();
        film = new Film();
        film.setId(1L);
        film.setName("Film1");
        film.setDescription("Descr1");
        film.setReleaseDate(LocalDate.of(2024, 7, 23));
        film.setDuration(90L);
    }

    
    @Test
    void createFilm() {
        filmController.createFilm(film);
        assertEquals(filmController.findAll().toArray()[0], film);
    }

    @Test
    void updateFilm() {
        filmController.createFilm(film);
        film.setDuration(100L);
        film.setId(1L);
        filmController.updateFilm(film);
        assertEquals(filmController.findAll().toArray()[0], film);
    }
}

