package ch.fhnw.edu.rental.persistence.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import ch.fhnw.edu.rental.model.Rental;
import ch.fhnw.edu.rental.model.User;
import ch.fhnw.edu.rental.persistence.RentalRepository;

@Repository
public class JpaRentalRepository extends JpaRepository<Rental> implements RentalRepository {

    public JpaRentalRepository() {
        repositoryClass = Rental.class;
    }

    @Override
    public List<Rental> findByUser(User user) {
        return user.getRentals();
    }

}
