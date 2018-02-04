package com.example.demo.service;

import com.example.demo.model.entity.FwUserEntity;
import com.example.demo.model.entity.User;
import com.example.demo.repository.DemoRepository;
import com.sun.xml.internal.bind.v2.model.core.ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private DemoRepository demoRepository;


    @Override
    @Transactional(readOnly = true)
    public User findUserById() throws Exception {
        return demoRepository.findById(1);
    }

    @Override
    @Transactional
    public FwUserEntity findFwUserById() throws Exception {
        return demoRepository.insertFwUser();
    }
}
