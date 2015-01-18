package com.debatz.gifts.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
	
	@Column(name = "brand", nullable = true, length = 70)
	private String brand;
	
	@Lob
	@Column(name = "details", nullable = true, columnDefinition = "text")
	private String details;
	
	@Column(name = "slug", unique = true, nullable = false)
	private String slug;
	
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
	

	public Gift(String name, String slug, String brand, String details, List<String> shopLinks, User owner) {
		super();
		
		this.name = name;
		this.slug = slug;
		this.brand = brand;
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


	public String getSlug() {
		return slug;
	}


	public void setSlug(String slug) {
		this.slug = slug;
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
