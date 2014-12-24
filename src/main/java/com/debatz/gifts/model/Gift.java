package com.debatz.gifts.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="GIFTS")
public class Gift implements Serializable
{

	private static final long serialVersionUID = -3240452031165466075L;
	
	@Id
    @Column(nullable=false)
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
	
	@Column(name = "name", nullable = false, length = 70)
	private String name;
	
	@Column(name = "details", nullable = true)
	private String details;
	
	@Column(name = "username", unique = true, nullable = false, length = 45)
	@ElementCollection(fetch = FetchType.LAZY)
	private Set<String> shopLinks;
	
	@ManyToOne
	private User booker;
	
	public Gift() {
		super();
	}
	
	
	public Gift(String name, String details, Set<String> shopLinks, User owner) {
		super();
		this.name = name;
		this.details = details;
		this.shopLinks = shopLinks;
		this.booker = owner;
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
	
	public String getDetails() {
		return details;
	}
	
	public void setDetails(String details) {
		this.details = details;
	}
	
	public Set<String> getShopLinks() {
		return shopLinks;
	}
	
	public void setShopLinks(Set<String> shopLinks) {
		this.shopLinks = shopLinks;
	}

	public User getBooker() {
		return booker;
	}

	public void setBooker(User booker) {
		this.booker = booker;
	}
}
