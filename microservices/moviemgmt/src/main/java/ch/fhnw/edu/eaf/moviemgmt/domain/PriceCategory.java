package ch.fhnw.edu.eaf.moviemgmt.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "pricecategories")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("PriceCategory")
@DiscriminatorColumn(name = "pricecategory_type")
public abstract class PriceCategory {
	@Id
	@GeneratedValue
	@Column(name = "pricecategory_id")
	private Long id;

	public abstract double getCharge(int daysRented);

	public abstract String getName();

	public int getFrequentRenterPoints(int daysRented) {
		return 1;
	}
}
