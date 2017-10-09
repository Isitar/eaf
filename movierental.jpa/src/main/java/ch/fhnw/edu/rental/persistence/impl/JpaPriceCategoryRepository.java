package ch.fhnw.edu.rental.persistence.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import ch.fhnw.edu.rental.model.PriceCategory;
import ch.fhnw.edu.rental.persistence.PriceCategoryRepository;

@Repository
public class JpaPriceCategoryRepository extends JpaRepository<PriceCategory> implements PriceCategoryRepository {
    public JpaPriceCategoryRepository() {
        this.repositoryClass = PriceCategory.class;
    }
}
