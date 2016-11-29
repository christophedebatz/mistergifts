package com.debatz.gifts.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(name = "gifts")
public class Gift implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(nullable = false)
    @TableGenerator(name = "giftSequence", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "giftSequence")
    private Integer id;

    @Column(name = "name", nullable = false, length = 70)
    private String name;

    @Column(name = "brand", nullable = true, length = 70)
    private String brand;

    @Column(name = "details", nullable = true, columnDefinition = "text")
    private String details;

    @Column(name = "picture", length = 255, nullable = true)
    private String picture;

    @Column(name = "slug", unique = true, nullable = false)
    private String slug;

    @Column(name = "shoplinks", unique = false, nullable = true, length = 255)
    @ElementCollection
    private List<String> shopLinks;

    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date modificationDate;

    @OneToMany
    private List<User> viewers;

    @ManyToOne
    private User booker;

    @ManyToOne
    private User owner;

    public Gift() {
        super();
    }

    public Gift(String name, String slug, String brand, String details, String picture, List<String> shopLinks, User owner, List<User> viewers) {
        super();

        this.name = name;
        this.slug = slug;
        this.brand = brand;
        this.details = details;
        this.picture = picture;
        this.shopLinks = shopLinks;
        this.owner = owner;
        this.viewers = viewers;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public List<String> getShopLinks() {
        return shopLinks;
    }

    public void setShopLinks(List<String> shopLinks) {
        this.shopLinks = shopLinks;
    }

    public User getBooker() {
        return booker;
    }

    public void setBooker(User booker) {
        this.booker = booker;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<User> getViewers() {
        return viewers;
    }

    public void setViewers(List<User> viewers) {
        this.viewers = viewers;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gift gift = (Gift) o;
        return Objects.equals(id, gift.id) &&
                Objects.equals(name, gift.name) &&
                Objects.equals(brand, gift.brand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, brand);
    }

    @Override
    public String toString() {
        return "Gift{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", details='" + details + '\'' +
                ", picture='" + picture + '\'' +
                ", slug='" + slug + '\'' +
                ", shopLinks=" + shopLinks +
                ", creationDate=" + creationDate +
                ", onlyViewer=" + viewers +
                ", booker=" + booker +
                ", owner=" + owner +
                '}';
    }
}
