package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public Collection<User> findAll() {
        log.info("Поступил запрос на получение списка всех пользователей");
        return userRepository.findAll();
    }

    public User createUser(User user) {
        log.info("Поступил запрос на создание пользователя {}", user);
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return userRepository.createUser(user);
    }

    public User updateUser(User newUser) {
        log.info("Поступил запрос на обновление пользователя {}", newUser);
        userRepository.getUserById(newUser.getId());
        return userRepository.updateUser(newUser);
    }

    public void addFriend(Long id, Long friendId) {
        log.info("Поступил запрос на добавление пользователя {} в друзья {}", id, friendId);
        Optional<User> user = userRepository.getUserById(id);
        Optional<User> friend = userRepository.getUserById(friendId);
        userRepository.addFriend(id, friendId);
    }

    public void removeFriend(Long id, Long friendId) {
        log.info("Поступил запрос на удаление пользователя {} из друзей {}", id, friendId);
        Optional<User> user = userRepository.getUserById(id);
        Optional<User> friend = userRepository.getUserById(friendId);
        userRepository.removeFriend(id, friendId);
    }

    public List<User> getFriends(Long id) {
        log.info("Поступил запрос на получение друзей пользователя {}", id);
        Optional<User> userById = userRepository.getUserById(id);
        return userRepository.getFriends(id);
    }


    public List<User> getCommonFriends(Long id, Long otherId) {
        return userRepository.getCommonFriends(id, otherId);
    }
}
