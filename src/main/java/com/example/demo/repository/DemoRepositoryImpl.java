package com.example.demo.repository;

import com.example.demo.model.entity.FwUserEntity;
import com.example.demo.model.entity.User;
import com.sun.xml.internal.bind.v2.model.core.ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;

@Repository
public class DemoRepositoryImpl implements DemoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DemoQueryRepository queryRepository;

    @Override
    public User findById(int id) throws Exception {
        return null;
    }

    @Override
    public FwUserEntity insertFwUser() throws Exception {
        FwUserEntity user = new FwUserEntity();
        user.setId(2L);
        user.setFirstName("first2");
        user.setLastName("last2");
        entityManager.merge(user);
        return queryRepository.findFwUser(user.getId());
    }
}
