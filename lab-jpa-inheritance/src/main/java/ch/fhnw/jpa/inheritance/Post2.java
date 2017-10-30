package ch.fhnw.jpa.inheritance;

import javax.persistence.Basic;
import javax.persistence.Entity;

@Entity
public class Post2 extends ChildTopic2 {
    @Basic
    private String content;

    protected Post2() {
    }

    public Post2(String title, String owner) {
        this(title, owner, null);
    }

    public Post2(String title, String owner, String content) {
        super(title, owner);
        this.content = content;
    }

}
