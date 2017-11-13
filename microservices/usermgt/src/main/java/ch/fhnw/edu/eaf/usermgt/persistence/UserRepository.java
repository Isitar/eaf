package ch.fhnw.edu.eaf.usermgt.persistence;

import org.springframework.data.repository.CrudRepository;
import ch.fhnw.edu.eaf.usermgt.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {

}
