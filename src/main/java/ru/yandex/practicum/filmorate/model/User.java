package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.filmorate.utils.Marker;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
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
    Set<Long> friends = new HashSet<>();

    public void addFriend(final Long userId) {
        friends.add(userId);
    }

    public void removeFriend(final Long userId) {
        friends.remove(userId);
    }
}
