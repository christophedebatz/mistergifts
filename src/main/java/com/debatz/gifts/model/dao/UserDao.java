package com.debatz.gifts.model.dao;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.debatz.gifts.model.User;

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
	
	@Transactional
	public List<User> getUsers(String... unlessNames) {
		Query query = this.em.createQuery("select u from User u where u.username not in :names", User.class);
		query.setParameter("names", Arrays.asList(unlessNames));
		
		try {
			return (List<User>) query.getResultList();
		} catch (Exception ex) {
			return null;
		}
	}

	@Transactional(readOnly = true)
	public User findByUserName(String username) {
		return this.em.find(User.class, username);
	}
}