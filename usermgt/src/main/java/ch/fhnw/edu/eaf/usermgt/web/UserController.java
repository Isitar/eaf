package ch.fhnw.edu.eaf.usermgt.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.fhnw.edu.eaf.usermgt.domain.User;
import ch.fhnw.edu.eaf.usermgt.persistence.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id) {
        if (!userRepo.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userRepo.findOne(id), HttpStatus.OK);
    }

    @GetMapping("create")
    public void createDummyData() {
        userRepo.save(new User("Lastname1", "Firstname1"));
        userRepo.save(new User("Lastname2", "Firstname2"));
        userRepo.save(new User("Lastname3", "Firstname3"));
        userRepo.save(new User("Lastname4", "Firstname4"));
        userRepo.save(new User("Lastname5", "Firstname5"));
    }

    @GetMapping("")
    public ResponseEntity<Iterable<User>> getUsers() {
        return new ResponseEntity<>(userRepo.findAll(), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<User> postUser(@RequestBody User u) {
        if (userRepo.exists(u.getId())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        User responseUser = userRepo.save(u);
        return new ResponseEntity<>(responseUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> putUser(@PathVariable long id, @RequestBody User u) {
        if (!userRepo.exists(id) || !u.getId().equals(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        User responseUser = userRepo.save(u);
        return new ResponseEntity<>(responseUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id) {
        userRepo.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
