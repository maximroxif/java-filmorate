package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.filmorate.utils.Marker;
import ru.yandex.practicum.filmorate.utils.NotBeforeDate;

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
    @NotBeforeDate
    @NotNull(groups = Marker.OnCreate.class)
    LocalDate releaseDate;
    @NotNull(groups = Marker.OnCreate.class)
    @Positive
    Long duration;
}
