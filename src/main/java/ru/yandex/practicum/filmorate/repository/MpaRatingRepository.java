package ru.yandex.practicum.filmorate.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.MpaRating;

import java.util.List;
import java.util.Optional;

@Repository
public class MpaRatingRepository extends BaseRepository<MpaRating> {

    private static final String FIND_ALL_QUERY = "SELECT * FROM rating_mpa";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM rating_mpa WHERE id = ?";

    public MpaRatingRepository(JdbcTemplate jdbcTemplate, RowMapper<MpaRating> mapper) {
        super(jdbcTemplate, mapper);
    }

    public List<MpaRating> findAll() {
        return findMany(FIND_ALL_QUERY);
    }

    public Optional<MpaRating> findById(Long id) {
        return findOne(FIND_BY_ID_QUERY, id);
    }

}
