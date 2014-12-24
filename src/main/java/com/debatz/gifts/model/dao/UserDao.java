package com.debatz.gifts.model.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

	@Transactional(readOnly = true)
	public User findByUserName(String username) 
	{	
		return this.em.find(User.class, username);
	}
}