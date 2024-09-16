package ru.yandex.practicum.filmorate.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.repository.mappers.GenreRowMapper;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Repository
public class JdbcGenreRepository implements GenreRepository {
    private final NamedParameterJdbcTemplate jdbc;
    private final GenreRowMapper mapper;

    @Override
    public Collection<Genre> findAll() {
        String sql = """
                     SELECT *
                     FROM GENRE;
                     """;
        return jdbc.query(sql, mapper);
    }

    @Override
    public Optional<Genre> getById(int id) {
        String sql = """
                     SELECT *
                     FROM GENRE
                     WHERE GENRE_ID = :genre_id;
                     """;
        SqlParameterSource parameter = new MapSqlParameterSource().addValue("genre_id", id);
        return Optional.ofNullable(jdbc.queryForObject(sql, parameter, mapper));
    }

    @Override
    public void saveGenre(Film film) {
        String sqlDelete = """
                           DELETE FROM FILM_GENRE
                           WHERE FILM_ID = :film_id;
                           """;
        SqlParameterSource parameterDelete = new MapSqlParameterSource("film_id", film.getId());
        jdbc.update(sqlDelete, parameterDelete);
        Long id = film.getId();
        Set<Genre> genres = film.getGenres();
        SqlParameterSource[] batch = new MapSqlParameterSource[genres.size()];
        int count = 0;
        for (Genre genre : genres) {
            isGenreNotExist(genre.getId());
            SqlParameterSource parameter = new MapSqlParameterSource()
                    .addValue("film_id", id)
                    .addValue("genre_id", genre.getId());
            batch[count++] = parameter;
        }
        String sqlInsert = """
                           INSERT INTO FILM_GENRE (FILM_ID, GENRE_ID)
                           VALUES (:film_id, :genre_id);
                           """;
        jdbc.batchUpdate(sqlInsert, batch);
    }

    @Override
    public void isGenreNotExist(int id) {
        try {
            getById(id).get().getName();
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Жанр с id = " + id + " не найден");
        }
    }
}
