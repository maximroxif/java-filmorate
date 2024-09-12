//package ru.yandex.practicum.filmorate.storage;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import ru.yandex.practicum.filmorate.exception.ValidationException;
//import ru.yandex.practicum.filmorate.model.Film;
//
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.Map;
//
//@Slf4j
//@Component
//public class InMemoryFilmStorage implements FilmStorage {
//
//    private final Map<Long, Film> films = new HashMap<>();
//
//    @Override
//    public Collection<Film> findAll() {
//        log.info("Вернули список всех фильмов");
//        return films.values();
//    }
//
//    @Override
//    public Film createFilm(Film film) {
//        film.setId(getNextId());
//        films.put(film.getId(), film);
//        log.info("Создан фильм {}", film);
//        return film;
//    }
//
//    @Override
//    public Film updateFilm(Film newFilm) {
//        if (films.containsKey(newFilm.getId())) {
//            Film oldFilm = films.get(newFilm.getId());
//            if (newFilm.getReleaseDate() != null) {
//                oldFilm.setReleaseDate(newFilm.getReleaseDate());
//            }
//            if (newFilm.getName() != null) {
//                oldFilm.setName(newFilm.getName());
//            }
//            if (newFilm.getDescription() != null) {
//                oldFilm.setDescription(newFilm.getDescription());
//            }
//            if (newFilm.getDuration() != null) {
//                oldFilm.setDuration(newFilm.getDuration());
//            }
//            films.put(oldFilm.getId(), oldFilm);
//            log.info("Обновлен фильм {}", oldFilm);
//            return oldFilm;
//        }
//        log.error("id {} не найден", newFilm.getId());
//        throw new ValidationException("id " + newFilm.getId() + " не найден");
//    }
//
//    @Override
//    public Film getFilmById(Long id) {
//        log.info("Поступил запрос на поиск фильма с id {}", id);
//        Film film = films.get(id);
//        if (film == null) {
//            log.error("Фильм с id {} не найден", id);
//            throw new ValidationException("Фильм с id " + id + " не найден");
//        }
//        log.info("Фильм с id {} успешно найден", id);
//        return film;
//    }
//
//    @Override
//    public long getNextId() {
//        long currentMaxId = films.keySet()
//                .stream()
//                .mapToLong(id -> id)
//                .max()
//                .orElse(0);
//        return ++currentMaxId;
//    }
//}
