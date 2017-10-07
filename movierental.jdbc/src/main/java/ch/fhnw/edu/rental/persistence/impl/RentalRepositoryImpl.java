package ch.fhnw.edu.rental.persistence.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ch.fhnw.edu.rental.model.Movie;
import ch.fhnw.edu.rental.model.Rental;
import ch.fhnw.edu.rental.model.User;
import ch.fhnw.edu.rental.persistence.MovieRepository;
import ch.fhnw.edu.rental.persistence.RentalRepository;
import ch.fhnw.edu.rental.persistence.UserRepository;

@Component
public class RentalRepositoryImpl extends JDBCBaseClass<Rental> implements RentalRepository {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private MovieRepository movieRepo;

	public RentalRepositoryImpl() {
		tableName = "RENTALS";
		identtyFieldname = "RENTAL_ID";
	}

	@SuppressWarnings("unused")
	private void initData() {
		Calendar cal = new GregorianCalendar(2016, GregorianCalendar.SEPTEMBER, 23);
		Rental r = new Rental(userRepo.findOne(1L), movieRepo.findOne(1L), 7, cal.getTime());
		save(r);
		cal = new GregorianCalendar(2016, GregorianCalendar.SEPTEMBER, 25);
		r = new Rental(userRepo.findOne(1L), movieRepo.findOne(2L), 6, cal.getTime());
		save(r);
		cal = new GregorianCalendar(2016, GregorianCalendar.SEPTEMBER, 27);
		r = new Rental(userRepo.findOne(3L), movieRepo.findOne(3L), 6, cal.getTime());
		save(r);
	}

	@Override
	public List<Rental> findByUser(User user) {

		return template.query("select * from " + tableName + " where USER_ID=?", (rs, row) -> createEntity(rs),
				user.getId());
	}

	@Override
	public Rental save(Rental rental) {
		if (!exists(rental.getId())) {
			rental.setId(getLastUsedId() + 1);

			template.update(
					"INSERT INTO " + tableName + "(rental_id, movie_id, user_id, rental_rentaldate, rental_rentaldays) "
							+ "VALUES (?,?,?,?,?)",
					rental.getId(), rental.getMovie().getId(), rental.getUser().getId(), rental.getRentalDate(),
					rental.getRentalDays());
		} else {
			template.update(
					"UPDATE " + tableName
							+ " SET movie_id=?, user_id=?, rental_rentaldate=?, rental_rentaldays=? where "
							+ identtyFieldname + "=?",
					rental.getMovie().getId(), rental.getUser().getId(), rental.getRentalDate(), rental.getRentalDays(),
					rental.getId());
		}
		return rental;
	}

	@Override
	protected Rental createEntity(ResultSet rs) throws SQLException {
		long userId = rs.getLong("USER_ID");
		long movieId = rs.getLong("MOVIE_ID");

		long rentalId = rs.getLong("RENTAL_ID");

		Rental rental = new Rental(rentalId,userRepo.findOne(userId), movieRepo.findOne(movieId), rs.getInt("RENTAL_RENTALDAYS"),
				rs.getDate("RENTAL_RENTALDATE"));
		
		return rental;
	}

}
