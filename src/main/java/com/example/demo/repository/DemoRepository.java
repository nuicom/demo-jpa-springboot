package com.example.demo.repository;

import com.example.demo.model.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface DemoRepository {
    User findById(int id) throws Exception;
//    FwUserEntity insertFwUser() throws Exception;
}
