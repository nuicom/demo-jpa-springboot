package com.example.demo.repository;

import com.example.demo.model.dto.FwUserSecurityBean;

import java.security.NoSuchAlgorithmException;

public interface DemoQueryRepository {
    //    FwUserEntity findFwUser(long id) throws Exception;
    String addFwUserToken(String userId) throws NoSuchAlgorithmException;

    FwUserSecurityBean getAccessTokenByRefreshToken(String refreshToken);
}
