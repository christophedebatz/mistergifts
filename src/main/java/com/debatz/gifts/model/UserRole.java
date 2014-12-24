package com.debatz.gifts.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "USER_ROLES",
	uniqueConstraints = @UniqueConstraint(
		columnNames = { "role", "username" }))
public class UserRole
{
 
	private Integer id;
	private User user;
	private String role;
	
 
	public UserRole() {
	}
 
	public UserRole(User user, String role) {
		this.user = user;
		this.role = role;
	}
 
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "user_role_id", 
		unique = true, nullable = false)
	public Integer getUserRoleId() {
		return this.id;
	}
 
	public void setUserRoleId(Integer userRoleId) {
		this.id = userRoleId;
	}
 
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "username", nullable = false)
	public User getUser() {
		return this.user;
	}
 
	public void setUser(User user) {
		this.user = user;
	}
 
	@Column(name = "role", nullable = false, length = 45)
	public String getRole() {
		return this.role;
	}
 
	public void setRole(String role) {
		this.role = role;
	}
 
}