package com.debatz.gifts.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.ManyToOne;

public class Gift implements Serializable
{

	private static final long serialVersionUID = -3240452031165466075L;
	
	private Long id;
	private String name;
	private String details;
	private List<String> shopLink;
	
	@ManyToOne
	private User booker;
	
	
	public Gift(String name, String details, List<String> shopLink, User owner) {
		super();
		this.name = name;
		this.details = details;
		this.shopLink = shopLink;
		this.booker = owner;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
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
	
	public List<String> getShopLink() {
		return shopLink;
	}
	
	public void setShopLink(List<String> shopLink) {
		this.shopLink = shopLink;
	}

	public User getBooker() {
		return booker;
	}

	public void setBooker(User booker) {
		this.booker = booker;
	}
}
