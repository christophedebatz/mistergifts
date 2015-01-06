package com.debatz.gifts.model.dao;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.debatz.gifts.model.User;

@Repository
public class UserDao
{

	@PersistenceContext
	private EntityManager em;
     
	@Transactional
    public String save(User person) {
        this.em.persist(person);
        return person.getUsername();
    }
	
	@Transactional
    public String update(User person) {
        this.em.merge(person);
        return person.getUsername();
    }
	
	@Transactional
	public List<User> getUsers() {
		Query query = this.em.createQuery("select u from User u");
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