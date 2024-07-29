package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final Map<Long, Film> films = new HashMap<>();

    @GetMapping
    public Collection<Film> findAll() {
        log.info("Получение списка всех фильмов");
        return films.values();
    }

    @PostMapping
    @Validated(Marker.OnCreate.class)
    public Film createFilm(@RequestBody @Valid Film film) {
        log.info("Поступил POST запрос /films на создание фильма {}", film);
        film.setId(getNextId());
        films.put(film.getId(), film);
        log.info("Отправлен POST ответ /films {}", film);
        return film;
    }


    @PutMapping
    @Validated({Marker.OnUpdate.class})
    public Film updateFilm(@RequestBody @Valid Film newFilm) {
        log.info("Поступил PUT запрос /films на обновление фильма {}", newFilm);
        if (films.containsKey(newFilm.getId())) {
            Film oldFilm = films.get(newFilm.getId());
            if (newFilm.getReleaseDate() != null) {
                oldFilm.setReleaseDate(newFilm.getReleaseDate());
            }
            if (newFilm.getName() != null) {
                oldFilm.setName(newFilm.getName());
            }
            if (newFilm.getDescription() != null) {
                oldFilm.setDescription(newFilm.getDescription());
            }
            if (newFilm.getDuration() != null) {
                oldFilm.setDuration(newFilm.getDuration());
            }
            films.put(oldFilm.getId(), oldFilm);
            log.info("Отправлен PUT ответ /films {}", newFilm);
            return oldFilm;
        }
        log.warn("id не найден");
        throw new ValidationException("id не найден");
    }

    private long getNextId() {
        long currentMaxId = films.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
