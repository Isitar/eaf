package ch.fhnw.edu.rental.persistence.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ch.fhnw.edu.rental.model.Movie;
import ch.fhnw.edu.rental.persistence.MovieRepository;

@Repository
public class JpaMovieRepository extends JpaRepository<Movie> implements MovieRepository {

    public JpaMovieRepository() {
        repositoryClass = Movie.class;
    }

    @Override
    public List<Movie> findByTitle(String title) {
        TypedQuery<Movie> query = em.createQuery(
                "SELECT m FROM " + repositoryClass.getName() + " m WHERE m.title = :title", repositoryClass);
        query.setParameter("title", title);
        return query.getResultList();
    }

}
