package ch.fhnw.edu.eaf.usermgt.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;
    
    private String lastName;
    
    private String firstName;
    
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
