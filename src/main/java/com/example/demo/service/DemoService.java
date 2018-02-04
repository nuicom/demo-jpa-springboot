package com.example.demo.service;

import com.example.demo.model.entity.FwUserEntity;
import com.example.demo.model.entity.User;

public interface DemoService {
    User findUserById() throws Exception;

    FwUserEntity findFwUserById() throws Exception;
}
