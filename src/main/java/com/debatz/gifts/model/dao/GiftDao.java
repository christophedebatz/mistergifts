package com.debatz.gifts.model.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.debatz.gifts.model.Gift;
import com.debatz.gifts.model.User;

@Repository
public class GiftDao
{

	@PersistenceContext
	private EntityManager em;
    
	@Transactional
    public int save(Gift gift) {
        this.em.persist(gift);
        return gift.getId();
    }
	
	@Transactional
    public int update(Gift gift) {
        this.em.merge(gift);
        return gift.getId();
    }
	
	@Transactional(readOnly = true)
	public Gift findGift(Integer id) {	
		return this.em.find(Gift.class, id);
	}
}