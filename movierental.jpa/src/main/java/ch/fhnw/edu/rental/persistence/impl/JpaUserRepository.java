package ch.fhnw.edu.rental.persistence.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ch.fhnw.edu.rental.model.User;
import ch.fhnw.edu.rental.persistence.UserRepository;

@Repository
public class JpaUserRepository extends JpaRepository<User> implements UserRepository {

    public JpaUserRepository() {
        repositoryClass = User.class;
    }

    @Override
    public List<User> findByLastName(String lastName) {
        TypedQuery<User> query = em.createQuery(
                "SELECT m FROM " + repositoryClass.getName() + " m WHERE m.lastName = :lastName", repositoryClass);
        query.setParameter("lastName", lastName);
        return query.getResultList();
    }

    @Override
    public List<User> findByFirstName(String firstName) {
        TypedQuery<User> query = em.createQuery(
                "SELECT m FROM " + repositoryClass.getName() + " m WHERE m.firstName = :firstName", repositoryClass);
        query.setParameter("firstName", firstName);
        return query.getResultList();
    }

    @Override
    public List<User> findByEmail(String email) {
        TypedQuery<User> query = em.createQuery(
                "SELECT m FROM " + repositoryClass.getName() + " m WHERE m.email = :email", repositoryClass);
        query.setParameter("email", email);
        return query.getResultList();
    }
}
