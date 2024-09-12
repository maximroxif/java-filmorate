package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.service.MpaRatingService;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/mpa")
@AllArgsConstructor
public class MpaRatingController {
    private final MpaRatingService mpaRatingService;

    @GetMapping
    public Collection<MpaRating> findAll() {
        return mpaRatingService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<MpaRating> findById(@PathVariable("id") Long id) {
        return mpaRatingService.findById(id);
    }
}
