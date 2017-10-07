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
public class MovieRepositoryImpl extends JDBCBaseClass<Movie> implements MovieRepository {

	@Autowired
	private PriceCategoryRepository priceCategoryRepo;

	public MovieRepositoryImpl() {
		tableName = "MOVIES";
		identtyFieldname = "MOVIE_ID";
	}

	protected Movie createEntity(ResultSet rs) throws SQLException {
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
	public List<Movie> findByTitle(String name) {
		return template.query("select * from " + tableName + " where MOVIE_TITLE = ?", (rs, row) -> createEntity(rs),
				name);
	}

	@Override
	public Movie save(Movie movie) {
		if (!exists(movie.getId())) {
			movie.setId(getLastUsedId() + 1);

			template.update(
					"INSERT INTO " + tableName
							+ "(MOVIE_ID, MOVIE_RELEASEDATE, MOVIE_TITLE, MOVIE_RENTED, PRICECATEGORY_FK) "
							+ "VALUES (?,?,?,?,?)",
					movie.getId(), movie.getReleaseDate(), movie.getTitle(), movie.isRented(),
					movie.getPriceCategory().getId());
		} else {
			template.update(
					"UPDATE MOVIES SET MOVIE_TITLE=?, MOVIE_RELEASEDATE=?,MOVIE_RENTED=?, PRICECATEGORY_FK=? where MOVIE_ID=?",
					movie.getTitle(), movie.getReleaseDate(), movie.isRented(), movie.getPriceCategory().getId(),
					movie.getId());
		}
		return movie;
	}
}
