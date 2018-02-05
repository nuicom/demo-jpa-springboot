package com.example.demo.repository;

import com.example.demo.model.dto.FwUserSecurityBean;
import com.example.demo.model.entity.FwUserSecurityEntity;
import com.example.demo.model.entity.FwUserTokenEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Repository
public class DemoQueryRepositoryImpl implements DemoQueryRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public String addFwUserToken(String userId) throws NoSuchAlgorithmException {
        final SecureRandom secureRandom = SecureRandom.getInstanceStrong();
        final String charsRandom = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        final StringBuilder randomStr = new StringBuilder(256);
        String refreshToken = null;
        for (int count = 0; count < 5 && refreshToken == null; ++count) {
            while (randomStr.length() < 255) {
                randomStr.append(charsRandom.charAt(secureRandom.nextInt(charsRandom.length())));
            }
            refreshToken = randomStr.toString();
            final Long duplicate = entityManager.createQuery(
                    "select count(fut.refreshToken) from FwUserTokenEntity fut where fut.refreshToken = :refreshToken",
                    Long.class).setParameter("refreshToken", refreshToken).getSingleResult();
            if (duplicate != null && duplicate <= 0) {
                final FwUserTokenEntity fwUserToken = new FwUserTokenEntity();
                fwUserToken.setRefreshToken(refreshToken);
                fwUserToken.setUserId(userId);
                final Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.HOUR_OF_DAY, 1);
                fwUserToken.setExpireTime(calendar.getTime());
                entityManager.persist(fwUserToken);
            }
        }
        if (refreshToken != null) {
            return refreshToken;
        } else {
            throw new IllegalStateException("can not random refresh token!");
        }
    }

    @Override
    @Transactional
    public FwUserSecurityBean getAccessTokenByRefreshToken(String refreshToken) {
        final TypedQuery<FwUserTokenEntity> query = entityManager.createQuery(
                "select fut from FwUserTokenEntity fut where fut.refreshToken = :refreshToken and fut.expireTime >= CURRENT_TIMESTAMP",
                FwUserTokenEntity.class);
        query.setParameter("refreshToken", refreshToken);
        return Optional.ofNullable(query.getResultList()).filter(futs -> !futs.isEmpty())
                .map(futs -> futs.get(0)).filter(fwUserToken -> fwUserToken != null
                        && fwUserToken.getExpireTime() != null && fwUserToken.getExpireTime().after(new Date()))
                .map(fwUserToken -> {
                    final FwUserSecurityEntity fwUserSecurity = Optional
                            .ofNullable(entityManager.find(FwUserSecurityEntity.class, fwUserToken.getUserId())).orElseGet(() -> {
                                final FwUserSecurityEntity fus = new FwUserSecurityEntity();
                                fus.setUserId(fwUserToken.getUserId());
                                final java.security.KeyPair rsaKeyPair = io.jsonwebtoken.impl.crypto.RsaProvider
                                        .generateKeyPair(2048);
                                final java.security.PrivateKey privateKey = rsaKeyPair.getPrivate();
                                fus.setPrivateKey("-----BEGIN RSA PRIVATE KEY-----\n"
                                        + Base64.getEncoder().encodeToString(privateKey.getEncoded())
                                        + "\n-----END RSA PRIVATE KEY-----");
                                final java.security.PublicKey publicKey = rsaKeyPair.getPublic();
                                fus.setPublicKey("-----BEGIN PUBLIC KEY-----\n"
                                        + Base64.getEncoder().encodeToString(publicKey.getEncoded())
                                        + "\n-----END PUBLIC KEY-----");
                                return entityManager.merge(fus);
                            });
//                    final TypedQuery<FwUser> queryFwUser = getEm()
//                            .createQuery("select fu from FwUser fu where fu.userId = :userId", FwUser.class);
//                    queryFwUser.setParameter("userId", fwUserToken.getUserId());
//                    final FwUser fwUser = queryFwUser.getSingleResult();
                    final FwUserSecurityBean dto = new FwUserSecurityBean();
                    dto.setUserId(fwUserSecurity.getUserId());
                    dto.setPrivateKey(fwUserSecurity.getPrivateKey());
                    dto.setPublicKey(fwUserSecurity.getPublicKey());
//                    dto.setType(fwUser.getType());
                    return dto;
                }).orElse(null);
    }

//    @Override
//    public FwUserEntity findFwUser(long id) throws Exception {
//        Query query = entityManager.createQuery("select user from FwUserEntity user where id = :id");
//        query.setParameter("id", id);
//        return (FwUserEntity) query.getSingleResult();
//    }
}
