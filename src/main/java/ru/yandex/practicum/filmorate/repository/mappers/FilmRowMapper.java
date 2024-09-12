package ru.yandex.practicum.filmorate.repository.mappers;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@AllArgsConstructor
public class FilmRowMapper implements RowMapper<Film> {

    private static final MpaRatingRowMapper mpaRatingRowMapper = new MpaRatingRowMapper();
    private static final GenreRowMapper genreRowMapper = new GenreRowMapper();
    private static final String FIND_MPA = "SELECT * FROM RATING_MPA WHERE ID = ?";
    private static final String FIND_GENRES = "SELECT * FROM FILM_GENRE WHERE FILM_ID = ?";
    private static final String FIND_GENRE = "SELECT * FROM GENRE WHERE ID = ?";
    private static final String FIND_LIKES = "SELECT USER_ID FROM FILM_LIKES WHERE FILM_ID = ?";
    protected final JdbcTemplate jdbcTemplate;

    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        Film film = new Film();
        film.setId(rs.getLong("ID"));
        film.setName(rs.getString("NAME"));
        film.setDescription(rs.getString("DESCRIPTION"));
        film.setReleaseDate(rs.getDate("RELEASEDATE").toLocalDate());
        film.setDuration(rs.getLong("DURATION"));
        film.setMpa(jdbcTemplate.queryForObject(FIND_MPA, mpaRatingRowMapper, rs.getLong("ID")));
        List<Long> genres = jdbcTemplate.query(FIND_GENRES, (rs1, rowNum1) -> {
               long num = rs1.getLong("GENRE_ID");
        return num;
    }, film.getId());
        for (Long genreId : genres) {
            film.getGenres().add(jdbcTemplate.queryForObject(FIND_GENRE, genreRowMapper, genreId));
        }
//        List<Long> likes = jdbcTemplate.query(FIND_LIKES, (rs1, rowNum1) -> {
//            long num = rs.getLong("USER_ID");
//            return num;
//        }, film.getId());
//        for (Long like : likes) {
//            film.get
//        }
        return film;
    }
}
