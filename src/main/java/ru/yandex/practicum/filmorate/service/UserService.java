package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final UserStorage userStorage;

    public Collection<User> findAll() {
        log.info("Поступил запрос на получение списка всех пользователей");
        return userStorage.findAll();
    }

    public User createUser(User user) {
        log.info("Поступил запрос на создание пользователя {}", user);
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        User user1 = userStorage.createUser(user);
        log.info("Создан пользователь {}", user1);
        return user1;
    }

    public User updateUser(User newUser) {
        log.info("Поступил запрос на обновление пользователя {}", newUser);
        try {
            return userStorage.updateUser(newUser);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ValidationException("id " + newUser.getId() + " не найден");
        }
    }

    public void addFriend(Long id, Long friendId) {
        log.info("Поступил запрос на добавление пользователя {} в друзья {}", id, friendId);
        try {
            User user = userStorage.getUserById(id);
            User friend = userStorage.getUserById(friendId);
            user.addFriend(friendId);
            friend.addFriend(user.getId());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ValidationException(e.getMessage());
        }
    }

    public void removeFriend(Long id, Long friendId) {
        log.info("Поступил запрос на удаление пользователя {} из друзей {}", id, friendId);
        try {
            User user = userStorage.getUserById(id);
            User friend = userStorage.getUserById(friendId);
            user.removeFriend(friendId);
            friend.removeFriend(user.getId());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ValidationException(e.getMessage());
        }
    }

    public List<User> getFriends(Long id) {
        log.info("Поступил запрос на получение друзей пользователя {}", id);
        return userStorage.getUserById(id).getFriends().stream()
                .map(userStorage::getUserById)
                .collect(Collectors.toList());
    }


    public List<User> getCommonFriends(Long id, Long otherId) {
        log.info("Поступил запрос на получение общих друзей пользователя {} с пользователем {}", id,otherId);
        Set<Long> friends = userStorage.getUserById(id).getFriends();
        Set<Long> friends1 = userStorage.getUserById(otherId).getFriends();
        return friends.stream().filter(friends1::contains)
                .map(userStorage::getUserById)
                .collect(Collectors.toList());
    }
}
