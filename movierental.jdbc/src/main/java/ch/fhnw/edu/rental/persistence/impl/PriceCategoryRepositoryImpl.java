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
public class PriceCategoryRepositoryImpl extends JDBCBaseClass<PriceCategory> implements PriceCategoryRepository> {

	@SuppressWarnings("unused")
	private void initData() {

		save(new PriceCategoryRegular());
		save(new PriceCategoryChildren());
		save(new PriceCategoryNewRelease());
	}

	@Override
	public PriceCategory save(PriceCategory category) {
		if (category.getId() == null)
			category.setId(nextId++);
		data.put(category.getId(), category);
		return category;
	}

	@Override
	public void delete(PriceCategory priceCategory) {
		if (priceCategory == null)
			throw new IllegalArgumentException();
		data.remove(priceCategory.getId());
		priceCategory.setId(null);
	}

	protected PriceCategory createEntity(ResultSet rs) throws SQLException {
		long id = rs.getLong("PRICECATEGORY_ID ");
		String type = rs.getString("PRICECATEGORY_TYPE");
		switch (type)
		{
		case "Children": return new PriceCategoryChildren(id);
		break;
		case "New Release": return new PriceCategoryNewRelease(id);
		break;
		case "Regular": return new PriceCategoryRegular(id);
		break;
		}
		return null;
	}

}
