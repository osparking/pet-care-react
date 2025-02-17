package com.bumsoap.petcare.model;

import com.bumsoap.petcare.utils.SystemUtils;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class VerifToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private Date expireDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * 유저에게 할당된 토큰에 대한 소멸 시점을 지정한다.
     * @param token 할당된 토큰
     * @param user 할당 받는 유저
     */
    public VerifToken(String token, User user) {
        this.token = token;
        this.user = user;
        this.expireDate = SystemUtils.getExpireTime();
    }

}
