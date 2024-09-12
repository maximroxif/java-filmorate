package ru.yandex.practicum.filmorate.repository;

import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Repository
public class FilmRepository extends BaseRepository<Film> {

    private static final String FIND_ALL_QUERY = "SELECT f.*, rm.NAME, fg.GENRE_ID, g.NAME " +
            "FROM FILMS f LEFT JOIN RATING_MPA as rm ON f.RATING_ID = rm.ID " +
            "LEFT JOIN FILM_GENRE fg ON f.ID = fg.FILM_ID " +
            "LEFT JOIN GENRE g ON fg.GENRE_ID = g.ID " +
            "LEFT JOIN FILM_LIKES fl ON f.ID = fl.FILM_ID ";

    private static final String FIND_BY_ID_QUERY = """
            SELECT f.*, rm.NAME, fg.GENRE_ID, g.NAME
                        FROM FILMS f JOIN RATING_MPA rm ON f.RATING_ID = rm.ID
                        LEFT JOIN FILM_GENRE fg ON f.ID = fg.FILM_ID
                        LEFT JOIN GENRE g ON fg.GENRE_ID = g.ID
                        LEFT JOIN FILM_LIKES fl ON f.ID = fl.FILM_ID
                        WHERE f.ID = ?;
            """;

    private static final String INSERT_QUERY = "INSERT INTO FILMS(NAME, DESCRIPTION, RELEASEDATE, DURATION, RATING_ID) " +
            "VALUES (?, ?, ?, ?, ?)";

    private static final String UPDATE_QUERY = "UPDATE FILMS " +
            "SET NAME = ?, DESCRIPTION = ?, RELEASEDATE = ?, DURATION = ?, RATING_ID = ? " +
            "WHERE ID = ? ";

    private static final String ADD_LIKE_QUERY = "INSERT INTO FILM_LIKES (FILM_ID, USER_ID)"
            + "VALUES (?, ?)";

    private static final String DELETE_LIKE_QUERY = "DELETE FROM FILM_LIKES WHERE FILM_ID = ? AND USER_ID = ?";

    private static final String GET_POPULAR_FILMS_QUERY =
            """
            WITH prep AS (
                         SELECT FILM_ID,
                                count(*) AS cnt
                           FROM FILM_LIKES
                          GROUP BY FILM_ID
                         )
            SELECT f.*\s
              FROM FILMS f
              left JOIN prep
                ON prep.FILM_ID = f.id\s
             ORDER BY prep.cnt DESC\s
             LIMIT ?
            """;

    private static final String ADD_FILM_GENRES = "INSERT INTO FILM_GENRE (FILM_ID, GENRE_ID) VALUES (?, ?)";

    private static final String DELETE_FILM_GENRES = "DELETE FROM FILM_GENRE WHERE FILM_ID = ?";


    public FilmRepository(JdbcTemplate jdbcTemplate, RowMapper<Film> rowMapper) {
        super(jdbcTemplate, rowMapper);
    }

    public List<Film> findAll() {
        return findMany(FIND_ALL_QUERY).stream().toList();
    }

    public Optional<Film> getById(Long id) {
        Optional<Film> film = findOne(FIND_BY_ID_QUERY, id);
        if (film.isPresent()) {
            return film;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Пользователь с id " + id + " не найден");
    }

    public Film createFilm(Film film) {
               Long idd = insert(INSERT_QUERY,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId());
               film.setId(idd);

        return film;
    }

    public Film updateFilm(Film film) {
        update(UPDATE_QUERY,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa() != null ? film.getMpa().getId() : null,
                film.getId());
        return film;
    }

    public void likeFilm(Long id, Long userId) {
        update(ADD_LIKE_QUERY, id, userId);
    }

    public void deleteFilmLike(Long id, Long userId) {
        delete(DELETE_LIKE_QUERY, id, userId);
    }

    public List<Film> getPopularFilms(Long count) {
        return findMany(GET_POPULAR_FILMS_QUERY, count);
//        return findMany(GET_POPULAR_FILMS_QUERY, count).stream().toList();
    }

    public void addGenre(Long filmId, Long genreId) {
        update(ADD_FILM_GENRES, filmId, genreId);
    }

    public void deleteGenre(Film film, Long genreId) {
        delete(DELETE_FILM_GENRES, film.getId(), genreId);
    }

    public Optional<Film> getFilmById(Long filmId) {
        Optional<Film> film = findOne(FIND_BY_ID_QUERY, filmId);
        if (film.isPresent()) {
            return film;
        }
        throw new ValidationException("Пользователь с id " + filmId + " не найден");
    }


}
