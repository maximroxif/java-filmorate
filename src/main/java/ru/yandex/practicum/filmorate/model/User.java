package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.filmorate.utils.Marker;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Null(groups = Marker.OnCreate.class)
    @NotNull(groups = Marker.OnUpdate.class)
    Long id;
    @Email
    @NotNull(groups = Marker.OnCreate.class)
    String email;
    @Pattern(regexp = "^[a-zA-Z0-9а-яА-Я._-]+$")
    @NotEmpty(groups = Marker.OnCreate.class)
    String login;
    String name;
    @NotNull(groups = Marker.OnCreate.class)
    @PastOrPresent
    LocalDate birthday;
}
