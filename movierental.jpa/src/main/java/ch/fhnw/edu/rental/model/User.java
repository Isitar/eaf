package ch.fhnw.edu.rental.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private Long id;
    @Column(name = "USER_NAME")
    private String lastName;
    @Column(name = "USER_FIRSTNAME")
    private String firstName;
    @Column(name = "USER_EMAIL")
    private String email;
    @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.REMOVE, CascadeType.MERGE })
    private List<Rental> rentals;

    public User() {

    }

    public User(String lastName, String firstName) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.rentals = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String name) {
        this.lastName = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    public int getCharge() {
        int result = 0;
        for (Rental rental : rentals) {
            result += rental.getMovie().getPriceCategory().getCharge(rental.getRentalDays());
        }
        return result;
    }

}
