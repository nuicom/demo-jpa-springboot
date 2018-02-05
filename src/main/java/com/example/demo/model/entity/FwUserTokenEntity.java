package com.example.demo.model.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "FW_USER_TOKEN", schema = "SYSTEM", catalog = "")
public class FwUserTokenEntity {
    private Long id;
    private String userId;
    private Date expireTime;
    private String refreshToken;

    @Id
    @SequenceGenerator(name = "FW_USER_TOKEN_SEQ_GENERATOR", sequenceName = "FW_USER_TOKEN_SEQ_GENERATOR", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FW_USER_TOKEN_SEQ_GENERATOR")
    @Column(name = "ID", nullable = false, precision = 0)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "USER_ID", nullable = true, length = 20)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "EXPIRE_TIME", nullable = true)
    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    @Basic
    @Column(name = "REFRESH_TOKEN", nullable = true, length = 2000)
    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FwUserTokenEntity that = (FwUserTokenEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(expireTime, that.expireTime) &&
                Objects.equals(refreshToken, that.refreshToken);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userId, expireTime, refreshToken);
    }
}
