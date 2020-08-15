package org.hibernate.bugs;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Entity;

@Entity
public class Foo {

    private long id;
    private Date startTime;

    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    public long getId() {
        return this.id;
    }
    public void setId(final long id) {
        this.id = id;
    }

    public Date getStartTime() {
        return this.startTime;
    }
    public void setStartTime(final Date startTime) {
        this.startTime = startTime;
    }
}
