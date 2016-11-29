package com.debatz.gifts.model.dao;

import com.debatz.gifts.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Repository
public class UserDao extends AbstractDao<User, Integer>
{
	public UserDao() {
		super(User.class);
	}

	@PostConstruct
	public void initialize() {
		this.em.clear();
		this.em.getEntityManagerFactory().getCache().evictAll();
	}

	@Transactional
	public List<User> getUsers() {
		Query query = this.em.createQuery("select u from User u");
		query.setHint("javax.persistence.cache.retrieveMode", "BYPASS");

		try {
			return (List<User>) query.getResultList();
		} catch (Exception ex) {
			return null;
		}
	}
	
	public List<User> getUsers(String... unlessNames) {
		Objects.requireNonNull(unlessNames);
		Query query = this.em.createQuery("select u from User u where u.username not in :names", User.class);
		query.setParameter("names", Arrays.asList(unlessNames));
		
		try {
			return (List<User>) query.getResultList();
		} catch (Exception ex) {
			return new ArrayList<>();
		}
	}

	public List<User> getUsersByNames(List<String> names) {
		if (names == null || names.isEmpty()) {
			return new ArrayList<>();
		}

		Query query = em.createQuery("select u from User u where u.username in :names", User.class);
		query.setParameter("names", names);

		try {
			return (List<User>) query.getResultList();
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	@Transactional(readOnly = true)
	public User findByUserName(String username) {
		return this.em.find(User.class, username);
	}
}