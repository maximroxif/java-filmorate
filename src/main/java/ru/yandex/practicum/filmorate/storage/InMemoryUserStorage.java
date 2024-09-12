//package ru.yandex.practicum.filmorate.storage;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import ru.yandex.practicum.filmorate.exception.ValidationException;
//import ru.yandex.practicum.filmorate.model.User;
//
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.Map;
//
//@Slf4j
//@Component
//public class InMemoryUserStorage implements UserStorage {
//
//    private final Map<Long, User> users = new HashMap<>();
//
//    @Override
//    public Collection<User> findAll() {
//        log.info("Вернули список всех пользователей");
//        return users.values();
//    }
//
//    @Override
//    public User createUser(User user) {
//        user.setId(getNextId());
//        users.put(user.getId(), user);
//        log.info("Создан пользователь {}", user);
//        return user;
//    }
//
//    @Override
//    public User updateUser(User newUser) {
//        if (users.containsKey(newUser.getId())) {
//            User oldUser = users.get(newUser.getId());
//            if (newUser.getName() == null || newUser.getName().isBlank()) {
//                oldUser.setName(newUser.getLogin());
//            } else {
//                oldUser.setName(newUser.getName());
//            }
//            if (newUser.getLogin() != null) {
//                oldUser.setLogin(newUser.getLogin());
//            }
//            if (newUser.getEmail() != null) {
//                oldUser.setEmail(newUser.getEmail());
//            }
//            if (newUser.getBirthday() != null) {
//                oldUser.setBirthday(newUser.getBirthday());
//            }
//            log.info("Обновлен пользователь {}", oldUser);
//            return oldUser;
//        }
//        log.error("Пользователь с id {} не найден", newUser.getId());
//        throw new ValidationException("id " + newUser.getId() + " не найден");
//    }
//
//    public User getUserById(long id) {
//        log.info("Поступил запрос на поиск пользователя с id {}", id);
//        User user = users.get(id);
//        if (user == null) {
//            log.error("Пользователь с id {} не найден", id);
//            throw new ValidationException("Пользователь с id " + id + " не найден");
//        }
//        log.info("Пользователь с id {} успешно найден", id);
//        return user;
//    }
//
//    @Override
//    public long getNextId() {
//        long currentMaxId = users.keySet()
//                .stream()
//                .mapToLong(id -> id)
//                .max()
//                .orElse(0);
//        return ++currentMaxId;
//    }
//}
