package ch.fhnw.jpa.inheritance;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class ChildTopic2 extends MotherTopic {

    private String title;
    private String owner;

    protected ChildTopic2() {
    }

    public ChildTopic2(String title, String owner) {
        this.title = title;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOwner() {
        return owner;
    }

}
