package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.filmorate.utils.Marker;
import ru.yandex.practicum.filmorate.utils.NotBeforeDate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class Film {
    @Null(groups = Marker.OnCreate.class)
    @NotNull(groups = Marker.OnUpdate.class)
    Long id;
    @NotEmpty(groups = Marker.OnCreate.class)
    String name;
    @NotEmpty(groups = Marker.OnCreate.class)
    @Size(max = 200)
    String description;
    @NotBeforeDate
    @NotNull(groups = Marker.OnCreate.class)
    LocalDate releaseDate;
    @NotNull(groups = Marker.OnCreate.class)
    @Positive
    Long duration;
    MpaRating mpa;
    LinkedHashSet<Genre> genres;
}
