package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.filmorate.controller.Marker;
import ru.yandex.practicum.filmorate.utils.DateAnnatation;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Film {
    @Null(groups = Marker.OnCreate.class)
    @NotNull(groups = Marker.OnUpdate.class)
    Long id;
    @NotEmpty(groups = Marker.OnCreate.class)
    String name;
    @NotEmpty(groups = Marker.OnCreate.class)
    @Size(max = 200)
    String description;
    @DateAnnatation
    @NotNull(groups = Marker.OnCreate.class)
    LocalDate releaseDate;
    @NotNull(groups = Marker.OnCreate.class)
    @Positive
    Long duration;
}
