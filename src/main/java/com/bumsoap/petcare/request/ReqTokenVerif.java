package com.bumsoap.petcare.request;

import com.bumsoap.petcare.model.User;
import lombok.Data;

import java.util.Date;

@Data
public class ReqTokenVerif {
    private String token;
    private Date expireTime;
    private User user;

}
