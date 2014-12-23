package com.debatz.gifts.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
 

@Entity
@Table(name="USERS")
public class User implements Serializable
{
	
	private static final long serialVersionUID = 7782523927070305419L;
	
	
	@Id
    @GeneratedValue
    private Long id;
    private String username;
    private String password;
    private boolean enabled;
    
    @OneToMany
    private List<Gift> ownedGifts;
    
    @OneToMany(mappedBy="user")
    private List<Gift> bookedGifts;
   
    
    public User(String username, String password, boolean enabled) {		
		this.username = username;
		this.password = password;
		this.enabled = enabled;
	}

    
    @Id
	@Column(name = "username", unique = true, nullable = false, length = 45)
	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}
	
	
	@Column(name = "password", nullable = false, length = 60)
	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	
	@Column(name = "enabled", nullable = false)
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


	public List<Gift> getBookedGifts() {
		return bookedGifts;
	}


	public void setBookedGifts(List<Gift> bookedGifts) {
		this.bookedGifts = bookedGifts;
	}
}