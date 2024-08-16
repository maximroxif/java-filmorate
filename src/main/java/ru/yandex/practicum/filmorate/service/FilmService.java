package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public Collection<Film> findAll() {
        log.info("Поступил запрос на получение списка всех фильмов");
        return filmStorage.findAll();
    }

    public Film createFilm(Film film) {
        log.info("Поступил запрос на создание фильма {}", film);
        Film film1 = filmStorage.createFilm(film);
        log.info("Создан фильм {}", film);
        return film1;
    }

    public Film updateFilm(Film newFilm) {
        log.info("Поступил запрос на обновление фильма {}", newFilm);
        try {
            return filmStorage.updateFilm(newFilm);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ValidationException("id " + newFilm.getId() + " не найден");
        }
    }

    public void likeFilm(Long filmId, Long userId) {
        log.info("Поступил запрос на добавление лайка фильму {} от пользователя {}", filmId, userId);
        Film film = filmStorage.getFilmById(filmId);
        try {
        User user = userStorage.getUserById(userId);
            film.addLike(user.getId());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ValidationException("Пользователь с id " + userId + " не найден");
        }
    }

    public void deleteFilmLike(Long filmId, Long userId) {
        log.info("Поступил запрос на удалеие лайка фильму {} от пользователя {}", filmId, userId);
        Film film = filmStorage.getFilmById(filmId);
        try {
        User user = userStorage.getUserById(userId);
        film.removeLike(user.getId());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ValidationException("Пользователь с id " + userId + " не найден");

        }
    }


    public List<Film> getPopularFilms(Long count) {
        log.info("Поступил запрос на получение {} популярного(ых) фильма(ов)", count);
        return filmStorage.findAll().stream()
                .sorted(Comparator.comparing((Film film) -> film.getLikes().size()).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }
}

