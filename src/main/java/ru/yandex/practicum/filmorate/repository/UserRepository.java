package ru.yandex.practicum.filmorate.repository;

import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository extends BaseRepository<User> {

    private static final String FIND_ALL_QUERY = "SELECT * FROM USERS";

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM USERS WHERE ID = ?";
    private static final String INSERT_QUERY = "INSERT INTO USERS (EMAIL, LOGIN, NAME, BIRTHDAY) " +
            "VALUES (?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE USERS SET " +
            "EMAIL = ?, " +
            "LOGIN = ?, " +
            "NAME = ?, " +
            "BIRTHDAY = ? " +
            "WHERE ID = ?";

    private static final String INSERT_FRIEND_QUERY = "INSERT INTO FRIENDS (USER_ID, FRIEND_ID) VALUES (?, ?)";

    private static final String FIND_FRIENDS_QUERY = "SELECT * FROM USERS WHERE ID IN " +
            "(SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID = ?)";

    private static final String DELETE_FRIEND_QUERY = "DELETE FROM FRIENDS WHERE USER_ID = ? and FRIEND_ID = ?";

    private static final String FIND_COMMON_FRIENDS_QUERY = "SELECT * FROM USERS WHERE ID IN " +
            "(SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID = ?) " +
            "AND ID IN " +
            "(SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID = ?)";

    public UserRepository(JdbcTemplate jdbcTemplate, RowMapper<User> mapper) {
        super(jdbcTemplate, mapper);
    }

    public List<User> findAll() {
        return findMany(FIND_ALL_QUERY);
    }

    public Optional<User> getUserById(long id) {
        Optional<User> user = findOne(FIND_BY_ID_QUERY, id);
        if (user.isPresent()) {
            return user;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Пользователь с id " + id + " не найден");
    }

    public User createUser(User user) {
     long id = insert(
                INSERT_QUERY,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday()
        );
        user.setId(id);
        return user;
    }

    public User updateUser(User user) {
        update(
                UPDATE_QUERY,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId()
        );
        return user;
    }

    public void addFriend(Long id, Long friendId) {
        update(INSERT_FRIEND_QUERY, id, friendId);
    }

    public void removeFriend(Long id, Long friendId) {
        delete(DELETE_FRIEND_QUERY, id, friendId);
    }

    public List<User> getFriends(Long id) {
        return findMany(FIND_FRIENDS_QUERY, id);
    }

    public List<User> getCommonFriends(Long id, Long otherId) {
        return findMany(FIND_COMMON_FRIENDS_QUERY, id, otherId);
    }
}
