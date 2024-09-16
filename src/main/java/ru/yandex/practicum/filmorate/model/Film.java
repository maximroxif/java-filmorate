package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.filmorate.utils.Marker;
import ru.yandex.practicum.filmorate.utils.NotBeforeDate;

import java.time.LocalDate;
import java.util.LinkedHashSet;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class Film {
    @Null(groups = Marker.OnCreate.class)
    @NotNull(groups = Marker.OnUpdate.class)
    Long id;
    @NotBlank
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
    @NotNull MpaRating mpa;
    @Builder.Default
    LinkedHashSet<Genre> genres = new LinkedHashSet<>();
}
