package ru.yandex.practicum.filmorate.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.mappers.FilmRowMapper;
import ru.yandex.practicum.filmorate.repository.mappers.FilmsRowMapper;

import java.sql.Date;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Repository
public class JdbcFilmRepository implements FilmRepository {

    private final NamedParameterJdbcOperations jdbc;
    private final FilmRowMapper filmExtractor;
    private final FilmsRowMapper filmsExtractor;

    @Override
    public Map<Long,Film> findAll() {
        String sql = """
                     SELECT *
                     FROM FILMS AS f
                     LEFT JOIN RATING_MPA AS r ON  f.MPA_ID = r.MPA_ID
                     LEFT JOIN FILM_GENRE AS fg ON f.FILM_ID = fg.FILM_ID
                     LEFT JOIN GENRE AS g ON fg.GENRE_ID = g.GENRE_ID;
                     """;
        return jdbc.query(sql, Map.of(), filmsExtractor);
    }

    @Override
    public Optional<Film> getById(Long id) {
        String sql = """
                     SELECT *
                     FROM FILMS AS f
                     LEFT JOIN RATING_MPA AS r ON  f.MPA_ID = r.MPA_ID
                     LEFT JOIN FILM_GENRE AS fg ON f.FILM_ID = fg.FILM_ID
                     LEFT JOIN GENRE AS g ON fg.GENRE_ID = g.GENRE_ID
                     WHERE f.FILM_ID = :film_id;
                     """;
        SqlParameterSource parameter = new MapSqlParameterSource("film_id", id);
        return Optional.ofNullable(jdbc.query(sql, parameter, filmExtractor));
    }

    @Override
    public void createFilm(Film film) {
        String sql = """
                     INSERT INTO FILMS (NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA_ID)
                     VALUES (:name, :description, :release_date, :duration, :mpa_id);
                     """;
        SqlParameterSource parameter = new MapSqlParameterSource()
                .addValue("name", film.getName())
                .addValue("description", film.getDescription())
                .addValue("release_date", Date.valueOf(film.getReleaseDate()))
                .addValue("duration", film.getDuration())
                .addValue("mpa_id", film.getMpa().getId());
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(sql, parameter, keyHolder);
        Long id = keyHolder.getKeyAs(Long.class);
        film.setId(id);
    }

    @Override
    public void updateFilm(Film film) {
        String sql = """
                     UPDATE FILMS
                     SET NAME = :name,
                     DESCRIPTION = :description,
                     RELEASE_DATE = :release_date,
                     DURATION = :duration,
                     MPA_ID = :mpa_id
                     WHERE FILM_ID = :film_id;
                     """;
        SqlParameterSource parameter = new MapSqlParameterSource()
                .addValue("name", film.getName())
                .addValue("description", film.getDescription())
                .addValue("release_date", Date.valueOf(film.getReleaseDate()))
                .addValue("duration", film.getDuration())
                .addValue("mpa_id", film.getMpa().getId())
                .addValue("film_id", film.getId());
        jdbc.update(sql, parameter);
    }

    @Override
    public void addLike(Long id, Long userId) {
        String sql = """
                     INSERT INTO FILM_LIKES (FILM_ID, USER_ID)
                     VALUES (:film_id, :user_id)
                     """;
        SqlParameterSource parameter = new MapSqlParameterSource()
                .addValue("film_id", id)
                .addValue("user_id", userId);
        jdbc.update(sql, parameter);
    }

    @Override
    public void removeLike(Long id, Long userId) {
        String sql = """
                     DELETE FROM FILM_LIKES
                     WHERE FILM_ID = :film_id
                           AND USER_ID = :user_id;
                     """;
        SqlParameterSource parameter = new MapSqlParameterSource()
                .addValue("film_id", id)
                .addValue("user_id", userId);
        jdbc.update(sql, parameter);
    }

    @Override
    public Map<Long, Film> getPopular(Long count) {
        String sql = """
                     SELECT COUNT(p.user_id) AS sum_likes,
                             f.FILM_ID,
                             f.NAME,
                             f.DESCRIPTION,
                             f.RELEASE_DATE,
                             f.DURATION,
                             f.MPA_ID,
                             r.MPA_NAME,
                             fg.GENRE_ID,
                             g.GENRE_NAME
                     FROM FILMS AS f
                             LEFT JOIN FILM_LIKES AS p ON f.film_id=p.film_id
                             LEFT JOIN RATING_MPA AS r ON f.MPA_ID = r.MPA_ID
                             LEFT JOIN FILM_GENRE AS fg ON f.FILM_ID = fg.FILM_ID
                             LEFT JOIN GENRE AS g ON fg.GENRE_ID = g.GENRE_ID
                     GROUP BY f.name, f.FILM_ID, f.NAME, f.DESCRIPTION, f.RELEASE_DATE,
                                                 f.DURATION, f.MPA_ID, r.MPA_NAME, fg.GENRE_ID, g.GENRE_NAME
                     ORDER BY COUNT(p.user_id) DESC
                     LIMIT :count;
                     """;
        SqlParameterSource parameter = new MapSqlParameterSource("count", count);
        return jdbc.query(sql, parameter, filmsExtractor);
    }

    @Override
    public void isFilmNotExists(Long id) {
        if (getById(id).isEmpty()) {
            throw new NotFoundException("Фильм с id = " + id + " не найден");
        }
    }



}