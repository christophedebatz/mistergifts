package com.debatz.gifts.model.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.debatz.gifts.model.Gift;

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
}