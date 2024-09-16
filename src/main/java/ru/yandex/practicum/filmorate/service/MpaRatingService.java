package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.repository.MpaRatingRepository;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class MpaRatingService {
    private final MpaRatingRepository mpaRepository;

    public Collection<MpaRating> findAll() {
        log.info("Получение списка рейтингов");
        return mpaRepository.findAll();
    }

    public Optional<MpaRating> getById(int id) {
        log.info("Получение рейтинга с id = {}", id);
        mpaRepository.isMpaExists(id);
        return mpaRepository.getById(id);
    }
}
