package ch.fhnw.jpa.inheritance;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class MotherTopic {
    @Id
    @GeneratedValue
    protected Long id;
}
