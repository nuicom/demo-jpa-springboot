package com.example.demo.model.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "FW_USER_SECURITY", schema = "SYSTEM", catalog = "")
public class FwUserSecurityEntity {
    private String userId;
    private String publicKey;
    private String privateKey;

    @Id
    @Column(name = "USER_ID", nullable = false, length = 20)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "PUBLIC_KEY", nullable = true, length = 2000)
    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    @Basic
    @Column(name = "PRIVATE_KEY", nullable = true, length = 2000)
    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FwUserSecurityEntity that = (FwUserSecurityEntity) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(publicKey, that.publicKey) &&
                Objects.equals(privateKey, that.privateKey);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userId, publicKey, privateKey);
    }
}
