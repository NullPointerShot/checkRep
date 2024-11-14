package com.senla.main.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<T, ID extends Serializable> {

    @PersistenceContext
    protected EntityManager entityManager;

    private final Class<T> clazz;

    protected AbstractDao(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T save(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    public T update(T entity) {
        return entityManager.merge(entity);
    }

    public void delete(ID id) {
        T entity = findById(id).orElseThrow();
        entityManager.remove(entity);
    }

    public Optional<T> findById(ID id) {
        return Optional.ofNullable(entityManager.find(clazz, id));
    }

    public List<T> findAll() {
        String jpql = "SELECT e FROM " + clazz.getSimpleName() + " e";
        TypedQuery<T> query = entityManager.createQuery(jpql, clazz);
        return query.getResultList();
    }
}
