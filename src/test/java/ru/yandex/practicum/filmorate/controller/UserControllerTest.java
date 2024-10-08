//package ru.yandex.practicum.filmorate.controller;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import ru.yandex.practicum.filmorate.model.User;
//import ru.yandex.practicum.filmorate.service.UserService;
//
//import java.time.LocalDate;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//class UserControllerTest {
//
//    UserService userService;
//    UserController userController;
//    User user;
//
//    @BeforeEach
//    void start() {
//        userController = new UserController(userService);
//        user = new User();
//        user.setId(1L);
//        user.setName("User");
//        user.setLogin("Userr");
//        user.setEmail("User@mail.ru");
//        user.setBirthday(LocalDate.of(2020, 7, 23));
//    }
//
//    @Test
//    void createFilm() {
//        userController.createUser(user);
//        assertEquals(userController.findAll().toArray()[0], user);
//    }
//
//    @Test
//    void updateFilm() {
//        userController.createUser(user);
//        user.setName("User1");
//        userController.updateUser(user);
//        assertEquals(userController.findAll().toArray()[0], user);
//    }
//}
