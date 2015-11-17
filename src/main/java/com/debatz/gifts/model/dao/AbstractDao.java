package com.debatz.gifts.model.dao;

import org.eclipse.persistence.sessions.Session;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

public abstract class AbstractDao<T, K>
{
    @PersistenceContext
    protected EntityManager em;

    protected Class entityClass;

    protected AbstractDao(Class entityClass) {
        this.entityClass = entityClass;
    }

    @Transactional
    public T save(T entity) {
        this.em.persist(entity);
        return entity;
    }

    @Transactional
    public T update(T entity) {
        this.em.merge(entity);
        return entity;
    }

    @Transactional
    public int getNextSequence() {
        return this.em.unwrap(Session.class)
                .getNextSequenceNumberValue(this.entityClass)
                .intValue();
    }

    @Transactional
    public boolean remove(K entityId) {
        this.em.remove(this.em.getReference(this.entityClass, entityId));
        return true;
    }

    @Transactional(readOnly = true)
    public T find(K entityId) {
        return (T) this.em.find(this.entityClass, entityId);
    }

    protected boolean getBooleanfromResult(Query query) {
        try {
            query.getSingleResult();
            return true;
        } catch (NoResultException ex) {
            return false;
        } catch (NonUniqueResultException ex) {
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
