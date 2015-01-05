package com.debatz.gifts.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
	
	@Column(name = "shoplinks", unique = false, nullable = true, length = 255)
	@ElementCollection(fetch=FetchType.EAGER)
	private List<String> shopLinks;
	
	@ManyToOne
	private User booker;
	
	@ManyToOne
	private User owner;
	
	
	public Gift() {
		super();
	}
	

	public Gift(String name, String details, List<String> shopLinks, User owner) {
		super();
		this.name = name;
		this.details = details;
		this.shopLinks = shopLinks;
		this.owner = owner;
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
}
