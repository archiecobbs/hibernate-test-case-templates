package org.hibernate.bugs;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Entity;

@Entity
public class Foo {

    private long id;
    private boolean bar;

    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    public long getId() {
        return this.id;
    }
    public void setId(final long id) {
        this.id = id;
    }

    public boolean isBar() {
        return this.bar;
    }
    public void setBar(final boolean bar) {
        this.bar = bar;
    }
}
