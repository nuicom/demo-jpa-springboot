package com.example.demo.repository;

import com.example.demo.model.entity.FwUserEntity;

public interface DemoQueryRepository {
    FwUserEntity findFwUser(long id) throws Exception;
}
