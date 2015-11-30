package com.debatz.gifts.model;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "USER_GIFT",
        uniqueConstraints = @UniqueConstraint(
                columnNames = { "role", "username" })
)
public class Participation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_gift_id", unique = true, nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private User user;

    @Column(name = "gift", nullable = false, length = 45)
    private Gift gift;

    @Column(nullable = false)
    private int participation;


    public Participation() {
    }

    public Participation(User user, Gift gift, int participation) {
        this.user = user;
        this.gift = gift;
        this.participation = participation;
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

    public Gift getGift() {
        return gift;
    }

    public void setGift(Gift gift) {
        this.gift = gift;
    }

    public int getParticipation() {
        return participation;
    }

    public void setParticipation(int participation) {
        this.participation = participation;
    }
}
