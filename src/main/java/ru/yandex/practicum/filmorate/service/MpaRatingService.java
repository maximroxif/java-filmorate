package ru.yandex.practicum.filmorate.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.repository.MpaRatingRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class MpaRatingService {
    private final MpaRatingRepository repository;
    public MpaRatingService(final MpaRatingRepository repository) {
        this.repository = repository;
    }

    public Collection<MpaRating> findAll() {
        return repository.findAll();
    }

    public Optional<MpaRating> findById(final Long id) {
        Optional<MpaRating> mpaRating = repository.findById(id);
        if (mpaRating.isPresent()) {
            return mpaRating;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Рейтинг с id " + id + " не найден.");
    }
}
