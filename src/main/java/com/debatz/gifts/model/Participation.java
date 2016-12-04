package com.debatz.gifts.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "participations", schema = "mistergifts")
public class Participation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_gift_id", unique = true, nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ParticipationType type;

    @Column(name = "value", nullable = false)
    private int value = 0;

    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Date date = new Date();

    public Participation() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ParticipationType getType() {
        return type;
    }

    public void setType(ParticipationType type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date creationDate) {
        this.date = creationDate;
    }
}
