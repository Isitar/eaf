package ch.fhnw.edu.eaf.rentalmgmt.web;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import ch.fhnw.edu.eaf.rentalmgmt.domain.Rental;
import ch.fhnw.edu.eaf.rentalmgmt.domain.dto.MovieDTO;
import ch.fhnw.edu.eaf.rentalmgmt.domain.dto.RentalDTO;
import ch.fhnw.edu.eaf.rentalmgmt.domain.dto.UserDTO;
import ch.fhnw.edu.eaf.rentalmgmt.persistence.RentalRepository;

@RestController
@RequestMapping("/rentals")
public class RentalController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RentalRepository rentalRepository;

    private final String userManagement = "usermanagement";
    private final String movieManagement = "moviemanagement";

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<RentalDTO>> findAll() {
        Sort sort = new Sort(Direction.ASC, "id");
        List<Rental> rentals = rentalRepository.findAll(sort);
        log.debug("Found " + rentals.size() + " rentals");

        return new ResponseEntity<List<RentalDTO>>(
                rentals.stream().map(r -> createRentalDTO(r)).collect(Collectors.toList()), HttpStatus.OK);

    }

    private RentalDTO createRentalDTO(Rental r) {
        MovieDTO mvDTO = null;

        try {
            mvDTO = restTemplate.getForObject("http://" + movieManagement + "/movies/" + r.getMovieId(), MovieDTO.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getStackTrace().toString());
        }

        UserDTO uDTO = null;
        try {
            uDTO = restTemplate.getForObject("http://" + userManagement + "/users/"+ r.getUserId(), UserDTO.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getStackTrace().toString());
        }
        return new RentalDTO(r, uDTO, mvDTO);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<RentalDTO> findById(@PathVariable Long id) {
        Rental rental = rentalRepository.findOne(id);
        if (rental == null) {
            return new ResponseEntity<RentalDTO>(HttpStatus.NOT_FOUND);
        }
        log.debug("Found rental with id=" + rental.getId());
        return new ResponseEntity<RentalDTO>(createRentalDTO(rental), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Rental> create(@Valid @RequestBody Rental rental, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<Rental>(HttpStatus.PRECONDITION_FAILED);
        }
        rental = rentalRepository.save(rental);
        log.debug("Created rental with id=" + rental.getId());
        return new ResponseEntity<Rental>(rental, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Rental> update(@Valid @RequestBody Rental newRental, @PathVariable Long id) {
        Rental rental = rentalRepository.findOne(id);
        if (rental == null) {
            return new ResponseEntity<Rental>(HttpStatus.NOT_FOUND);
        }
        rental.setRentalDate(newRental.getRentalDate());
        rental.setRentalDays(newRental.getRentalDays());
        rentalRepository.save(rental);
        log.debug("Updated rental with id=" + rental.getId());
        return new ResponseEntity<Rental>(rental, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable Long id) {
        rentalRepository.delete(id);
        log.debug("Deleted rental with id=" + id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

}
