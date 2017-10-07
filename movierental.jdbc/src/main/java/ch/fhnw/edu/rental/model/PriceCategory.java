package ch.fhnw.edu.rental.model;

public abstract class PriceCategory implements Entity {
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PriceCategory(Long id) {
		this.id = id;
	}

	public abstract double getCharge(int daysRented);

	public int getFrequentRenterPoints(int daysRented) {
		return 1;
	}
}
