package com.debatz.gifts.model.dao;

import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
	
	@Transactional
    public int update(Gift gift) {
        this.em.merge(gift);
        return gift.getId();
    }
	
	@Transactional(readOnly = true)
	public Gift findGift(Integer id) {	
		return this.em.find(Gift.class, id);
	}
	
	@Transactional(readOnly = true)
	public Gift findGift(String slug) {	
		Query query = this.em.createQuery("select g from Gift g where g.slug = :slug", Gift.class);
		query.setParameter("slug", slug);
		
		try {
			return (Gift) query.getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}
	
	@Transactional(readOnly = true)
	public boolean giftsExist() {	
		Query query = this.em.createQuery("select g from Gift g");
		
		try {
			query.getSingleResult();
			return true;
		} catch (NoResultException ex) {
			return false;
		} catch (NonUniqueResultException ex) {
			return true;
		}
	}
	
	@Transactional(readOnly = true)
	public boolean giftsExist(String... unlessNames) {	
		Query query = this.em.createQuery("select g from Gift g where g.owner not in :names", Gift.class);
		query.setParameter("names", Arrays.asList(unlessNames));
		
		try {
			query.getSingleResult();
			return true;
		} catch (NoResultException ex) {
			return false;
		} catch (NonUniqueResultException ex) {
			return true;
		}
	}
}