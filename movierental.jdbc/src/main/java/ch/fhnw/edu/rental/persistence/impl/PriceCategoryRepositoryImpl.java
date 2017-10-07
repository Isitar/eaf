package ch.fhnw.edu.rental.persistence.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ch.fhnw.edu.rental.model.Movie;
import ch.fhnw.edu.rental.model.PriceCategory;
import ch.fhnw.edu.rental.model.PriceCategoryChildren;
import ch.fhnw.edu.rental.model.PriceCategoryNewRelease;
import ch.fhnw.edu.rental.model.PriceCategoryRegular;
import ch.fhnw.edu.rental.persistence.PriceCategoryRepository;

@Component
public class PriceCategoryRepositoryImpl extends JDBCBaseClass<PriceCategory> implements PriceCategoryRepository {

	public PriceCategoryRepositoryImpl() {
		tableName = "pricecategories";
		identtyFieldname = "pricecategory_id";
	}

	@SuppressWarnings("unused")
	private void initData() {

		save(new PriceCategoryRegular());
		save(new PriceCategoryChildren());
		save(new PriceCategoryNewRelease());
	}

	@Override
	public PriceCategory save(PriceCategory category) {
		if (!exists(category.getId())) {
			category.setId(getLastUsedId() + 1);

			template.update("INSERT INTO " + tableName + "(pricecategory_id, pricecategory_type) " + "VALUES (?,?)",
					category.getId(), category.toString());
		} else {
			template.update("UPDATE " + tableName + " SET pricecategory_type=? where " + identtyFieldname + "=?",
					category.toString(), category.getId());
		}
		return category;
	}

	protected PriceCategory createEntity(ResultSet rs) throws SQLException {
		long id = rs.getLong("PRICECATEGORY_ID");
		String type = rs.getString("PRICECATEGORY_TYPE");
		switch (type) {
		case "Children":
			return new PriceCategoryChildren(id);
		case "New Release":
			return new PriceCategoryNewRelease(id);
		case "Regular":
			return new PriceCategoryRegular(id);
		}
		return null;
	}

}
