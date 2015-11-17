package com.debatz.gifts.model.dao;

import java.util.Arrays;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import com.debatz.gifts.model.Gift;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class GiftDao extends AbstractDao<Gift, Integer>
{
	public GiftDao() {
		super(Gift.class);
	}

	@Transactional(readOnly = true)
	public Gift findGift(String slug) {	
		Query query = this.em.createQuery("select g from Gift g where g.slug = :slug", this.entityClass);
		query.setParameter("slug", slug);
		
		try {
			return (Gift) query.getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}
	
	@Transactional(readOnly = true)
	public boolean giftsExist() {	
		Query query = this.em.createQuery("select g from Gift g", this.entityClass);
		
		return this.getBooleanfromResult(query);
	}
	
	@Transactional(readOnly = true)
	public boolean giftsExist(String... unlessNames) {	
		Query query = this.em.createQuery("select g from Gift g where g.owner not in :names", this.entityClass);
		query.setParameter("names", Arrays.asList(unlessNames));

		return this.getBooleanfromResult(query);
	}
}