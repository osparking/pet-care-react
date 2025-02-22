package com.bumsoap.petcare.model;

import com.bumsoap.petcare.utils.SystemUtils;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class PwdResetReq {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private Date expiresOnTm;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    public PwdResetReq(String token, User user) {
        this.token = token;
        this.user = user;
        this.expiresOnTm = SystemUtils.getExpireTime();
    }
}
