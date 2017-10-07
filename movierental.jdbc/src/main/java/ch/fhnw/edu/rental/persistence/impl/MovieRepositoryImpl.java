package ch.fhnw.edu.rental.persistence.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ch.fhnw.edu.rental.model.Movie;
import ch.fhnw.edu.rental.persistence.MovieRepository;
import ch.fhnw.edu.rental.persistence.PriceCategoryRepository;

@Component
public class MovieRepositoryImpl implements MovieRepository {
    @Autowired
    private JdbcTemplate template;
    @Autowired
    private PriceCategoryRepository priceCategoryRepo;

    @Override
    public Movie findOne(Long id) {
        if (id == null)
            throw new IllegalArgumentException();
        return template.query("select * from MOVIES where MOVIE_ID = ?", (rs, row) -> createMovie(rs), id).get(0);
    }

    private Movie createMovie(ResultSet rs) throws SQLException {
        long priceCategory = rs.getLong("PRICECATEGORY_FK");
        long id = rs.getLong("MOVIE_ID");
        boolean rented = rs.getBoolean("MOVIE_RENTED");
        Movie m = new Movie(rs.getString("MOVIE_TITLE"), rs.getDate("MOVIE_RELEASEDATE"),
                priceCategoryRepo.findOne(priceCategory));
        m.setId(id);
        m.setRented(rented);
        return m;
    }

    @Override
    public List<Movie> findAll() {
        return template.query("select * from MOVIES", (rs, row) -> createMovie(rs));
    }

    @Override
    public List<Movie> findByTitle(String name) {
        return template.query("select * from MOVIES where MOVIE_TITLE = ?", (rs, row) -> createMovie(rs), name);
    }

    @Override
    public Movie save(Movie movie) {
        if (!exists(movie.getId())) {

        } else {
            template.update(
                    "UPDATE MOVIES SET MOVIE_TITLE=?, MOVIE_RELEASEDATE=?,MOVIE_RENTED=?, PRICECATEGORY_FK=? where MOVIE_ID=?",
                    movie.getTitle(), movie.getReleaseDate(), movie.isRented(), movie.getPriceCategory().getId(),
                    movie.getId());
        }
        return movie;
    }

    @Override
    public void delete(Movie movie) {
        if (movie == null)
            throw new IllegalArgumentException();
        movie.setId(null);
    }

    @Override
    public void delete(Long id) {
        if (id == null)
            throw new IllegalArgumentException();
        delete(findOne(id));
    }

    @Override
    public boolean exists(Long id) {
        if (id == null)
            throw new IllegalArgumentException();
        return !template
                .query("SELECT TOP 1 MOVIE_ID FROM MOVIES WHERE MOVIE_ID = :id", (rs, row) -> createMovie(rs), id)
                .isEmpty();
    }

    @Override
    public long count() {
        return template.query("SELECT COUNT(*) as Cnt FROM MOVIES", (rs, row) -> rs.getLong("Cnt")).get(0);

    }

}
