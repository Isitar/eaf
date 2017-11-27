package ch.fhnw.edu.eaf.usermgt.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="USER")
public class User {
    @Id
    @GeneratedValue
    @Column(name="ID")
    private Long id;
    @Column(name="LAST_NAME")
    private String lastName;
    @Column(name="FIRST_NAME")
    private String firstName;
    @Column(name="EMAIL")
    private String email;
    
    User() { 
    }
    
    public User(String lastName, String firstName) {
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public Long getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

}
