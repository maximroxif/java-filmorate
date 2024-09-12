package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.FilmRepository;
import ru.yandex.practicum.filmorate.repository.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class FilmService {
    private final FilmRepository filmRepository;
    private final UserRepository userRepository;
    private final GenreService genreService;
    private final MpaRatingService mpaRatingService;

    public Collection<Film> findAll() {
        log.info("Поступил запрос на получение списка всех фильмов");
        return filmRepository.findAll();
    }

    public Optional<Film> findById(long id) {
        return filmRepository.getById(id);
    }

    public Film createFilm(Film film) {
        log.info("Поступил запрос на создание фильма {}", film);
        if(film.getMpa() != null) {
            if (film.getMpa().getId() > mpaRatingService.findAll().size()) {
                log.trace("Рейтинг MPA не неайден");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Рейтинг MPA не неайден");
            }
        }
        if (film.getGenres() != null) {
            for (Genre genre : film.getGenres()) {
                try {
                    genreService.findById(genre.getId());
                } catch (Exception e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Жанр не неайден");
                }
            }
        }
        Film createdFilm =  filmRepository.createFilm(film);
        if (film.getGenres() != null) {
            for (Genre genre : film.getGenres()) {
                filmRepository.addGenre(createdFilm.getId(), genre.getId());
            }
        }

        return createdFilm;
    }

    public Film updateFilm(Film newFilm) {
        log.info("Поступил запрос на обновление фильма {}", newFilm);
        filmRepository.getById(newFilm.getId());
        return filmRepository.updateFilm(newFilm);
    }

    public void likeFilm(Long filmId, Long userId) {
        log.info("Поступил запрос на добавление лайка фильму {} от пользователя {}", filmId, userId);
        Optional<Film> film = filmRepository.getById(filmId);
        Optional<User> user = userRepository.getUserById(userId);
        filmRepository.likeFilm(filmId, userId);
    }

    public void deleteFilmLike(Long filmId, Long userId) {
        log.info("Поступил запрос на удалеие лайка фильму {} от пользователя {}", filmId, userId);
        Optional<Film> film = filmRepository.getFilmById(filmId);
        Optional<User> user = userRepository.getUserById(userId);
        filmRepository.deleteFilmLike(filmId, userId);
    }


    public List<Film> getPopularFilms(Long count) {
        log.info("Поступил запрос на получение {} популярного(ых) фильма(ов)", count);
        return filmRepository.getPopularFilms(count);
    }
}

