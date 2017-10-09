package ch.fhnw.edu.rental.persistence.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import ch.fhnw.edu.rental.model.Entity;

public abstract class JDBCBaseClass<T extends Entity> {
    protected String tableName;
    protected String identtyFieldname;

    @Autowired
    protected JdbcTemplate template;

    public T findOne(Long id) {
        if (id == null)
            throw new IllegalArgumentException();
        return template.query("select * from " + tableName + " where " + identtyFieldname + " = ?",
                (rs, row) -> createEntity(rs), id).get(0);
    }

    protected abstract T createEntity(ResultSet rs) throws SQLException;

    public List<T> findAll() {
        return template.query("select * from " + tableName, (rs, row) -> createEntity(rs));
    }

    public void delete(T t) {
        if (t == null)
            throw new IllegalArgumentException();
        delete(t.getId());
        t.setId(null);
    }

    public void delete(Long id) {
        if (id == null)
            throw new IllegalArgumentException();

        template.update("DELETE FROM " + tableName + " WHERE " + identtyFieldname + " = ?", id);
        
    }

    public boolean exists(Long id) {
        if (id == null) {
            return false;
        }
        return !template.query("SELECT TOP 1 * FROM " + tableName + " WHERE " + identtyFieldname + " = ?",
                (rs, row) -> createEntity(rs), id).isEmpty();
    }

    public long count() {
        return template.query("SELECT COUNT(*) as Cnt FROM " + tableName, (rs, row) -> rs.getLong("Cnt")).get(0);
    }

    protected long getLastUsedId() {
        return template.query("SELECT MAX(" + identtyFieldname + ") as maxId FROM " + tableName,
                (rs, row) -> rs.getLong("maxId")).get(0);
    }
}
