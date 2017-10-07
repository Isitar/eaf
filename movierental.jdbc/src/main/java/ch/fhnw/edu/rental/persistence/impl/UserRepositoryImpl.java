package ch.fhnw.edu.rental.persistence.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ch.fhnw.edu.rental.model.Rental;
import ch.fhnw.edu.rental.model.User;
import ch.fhnw.edu.rental.persistence.RentalRepository;
import ch.fhnw.edu.rental.persistence.UserRepository;

@Component
public class UserRepositoryImpl extends JDBCBaseClass<User> implements UserRepository {

	public UserRepositoryImpl() {
		tableName = "USERS";
		identtyFieldname = "USER_ID";
	}

	@SuppressWarnings("unused")
	private void initData() {
		save(new User("Keller", "Marc"));
		save(new User("Knecht", "Werner"));
		save(new User("Meyer", "Barbara"));
		save(new User("Kummer", "Adolf"));

		findOne(1L).setEmail("marc.keller@gmail.com");
		findOne(2L).setEmail("werner.knecht@gmail.com");
		findOne(3L).setEmail("barbara.meyer@gmail.com");
		findOne(4L).setEmail("adolf.kummer@gmail.com");
	}

	@Override
	public User save(User user) {
		if (!exists(user.getId())) {
			user.setId(getLastUsedId() + 1);

			template.update(
					"INSERT INTO " + tableName + "(user_id, user_name, user_firstname, user_email) "
							+ "VALUES (?,?,?,?)",
					user.getId(), user.getLastName(), user.getFirstName(), user.getEmail());
		} else {
			template.update(
					"UPDATE " + tableName + " SET movie_id=?, user_name=?, user_firstname=?, user_email=? where "
							+ identtyFieldname + "=?",
					user.getLastName(), user.getFirstName(), user.getEmail(), user.getId());
		}
		return user;
	}

	@Override
	public List<User> findByLastName(String lastName) {
		return template.query("select * from " + tableName + " where user_name=?", (rs, row) -> createEntity(rs),
				lastName);
	}

	@Override
	public List<User> findByFirstName(String firstName) {
		return template.query("select * from " + tableName + " where user_firstname=?", (rs, row) -> createEntity(rs),
				firstName);
	}

	@Override
	public List<User> findByEmail(String email) {
		return template.query("select * from " + tableName + " where user_email=?", (rs, row) -> createEntity(rs),
				email);
	}

	@Override
	protected User createEntity(ResultSet rs) throws SQLException {
		User user = new User(rs.getString("user_name"), rs.getString("user_firstname"));
		user.setEmail(rs.getString("user_email"));
		user.setId(rs.getLong("user_id"));
		return user;
	}

}
