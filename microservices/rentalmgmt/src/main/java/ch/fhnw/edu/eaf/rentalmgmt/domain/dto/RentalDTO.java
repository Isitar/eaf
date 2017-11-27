package ch.fhnw.edu.eaf.rentalmgmt.domain.dto;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import ch.fhnw.edu.eaf.rentalmgmt.domain.Rental;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonAutoDetect
public class RentalDTO {

    private Long id;

    private MovieDTO movie;

    private UserDTO user;

    private Date rentalDate;

    private int rentalDays;

    public RentalDTO(Rental r, UserDTO u, MovieDTO m) {
        this.user = u;
        this.movie = m;
        this.rentalDays = r.getRentalDays();
        this.rentalDate = r.getRentalDate();
        this.id = r.getId();
    }
}
