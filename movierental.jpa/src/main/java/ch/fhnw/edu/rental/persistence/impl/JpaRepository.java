package ch.fhnw.edu.rental.persistence.impl;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import ch.fhnw.edu.rental.model.Movie;

public class JpaRepository<T> {
    @PersistenceContext
    protected EntityManager em;
    protected Class<T> repositoryClass;

    public T findOne(Long id) {
        return em.find(repositoryClass, id);
    }

    public List<T> findAll() {
        return em.createQuery("SELECT m FROM " + repositoryClass.getSimpleName() + " m", repositoryClass)
                .getResultList();
    }

    public T save(T entity) {
        return em.merge(entity);
    }

    public void delete(Long id) {
        em.remove(em.getReference(repositoryClass, id));
    }

    public void delete(T entity) {
        em.remove(entity);
    }

    public boolean exists(Long id) {
        TypedQuery<Long> q = em.createQuery(
                "SELECT COUNT(*) FROM " + repositoryClass.getSimpleName() + " e WHERE e.id = :id", Long.class);
        q.setParameter("id", id);
        return q.getSingleResult() > 0;
    }

    public long count() {

        return em.createQuery("SELECT COUNT(*) FROM " + repositoryClass.getSimpleName() + " e", Long.class)
                .getSingleResult();
    }

}
