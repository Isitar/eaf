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

	public PriceCategory() {
	}

	public abstract double getCharge(int daysRented);

	public int getFrequentRenterPoints(int daysRented) {
		return 1;
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof PriceCategory)) {
			return false;
		}
		PriceCategory otherPriceCategory = (PriceCategory) other;
		return otherPriceCategory.id.equals(id) && otherPriceCategory.toString().equals(toString());
	}
}
