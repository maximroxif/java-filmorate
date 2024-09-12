package ru.yandex.practicum.filmorate.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.repository.GenreRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class GenreService {
    private final GenreRepository repository;
    public GenreService(final GenreRepository repository) {
        this.repository = repository;
    }

    public Collection<Genre> findAll() {
        return repository.findAll();
    }

    public Optional<Genre> findById(final Long id) {
        Optional<Genre> genre = repository.findById(id);
        if (genre.isPresent()) {
            return genre;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Жанр с id " + id + " не найден.");
        }
    }
}
