package com.example.demo.repository;

import com.example.demo.model.entity.FwUserEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class DemoQueryRepositoryImpl implements DemoQueryRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public FwUserEntity findFwUser(long id) throws Exception {
        Query query = entityManager.createQuery("select user from FwUserEntity user where id = :id");
        query.setParameter("id", id);
        return (FwUserEntity) query.getSingleResult();
    }
}
