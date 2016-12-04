package com.debatz.gifts.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="USERS")
public class User implements Serializable
{
	private static final long serialVersionUID = 7782523927070305419L;
	
	@Id
	@Column(name = "username", unique = true, nullable = false, length = 45)
    private String username;
	
	@Column(name = "password", nullable = false, length = 60)
    private String password;
	
	@Column(name = "enabled", nullable = false)
    private boolean enabled;
    
    @OneToMany(mappedBy="owner", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Gift> ownedGifts = new ArrayList<Gift>();

	@OneToMany(mappedBy="booker", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private List<Gift> bookedGifts = new ArrayList<Gift>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<UserRole> roles = new ArrayList<UserRole>(0);
    
    
    
    public User() {
		super();
	}

	public User(String username, String password, boolean enabled) {		
		this.username = username;
		this.password = password;
		this.enabled = enabled;
	}
	
	public User(String username, String password, boolean enabled, List<UserRole> roles) {
			this.username = username;
			this.password = password;
			this.enabled = enabled;
			this.roles = roles;
		}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<Gift> getOwnedGifts() {
		return ownedGifts;
	}

	public void setOwnedGifts(List<Gift> ownedGifts) {
		this.ownedGifts = ownedGifts;
	}
	
	public void addOwnedGift(Gift ownedGift) {
		this.ownedGifts.add(ownedGift);
	}

	public List<Gift> getBookedGifts() {
		return bookedGifts;
	}

	public void setBookedGifts(List<Gift> bookedGifts) {
		this.bookedGifts = bookedGifts;
	}
	
	public void addBookedGift(Gift bookedGift) {
		this.bookedGifts.add(bookedGift);
	}

	public List<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(List<UserRole> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		
		String booked = null;
		String owned  = null;
		
		for (Gift g : this.bookedGifts) {
			booked += g.getName() + ",";
		}
		
		for (Gift g : this.ownedGifts) {
			owned += g.getName() + ",";
		}
		
		return "User [username=" + username + ", password=" + password
				+ ", enabled=" + enabled + ", ownedGifts=" + owned
				+ ", bookedGifts=" + booked + ", roles=" + roles + "]";
	}
}