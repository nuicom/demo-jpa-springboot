package com.example.demo.repository;

import com.example.demo.model.entity.FwUserEntity;
import com.example.demo.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface DemoRepository {
    User findById(int id) throws Exception;
    FwUserEntity insertFwUser() throws Exception;
}
