package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PatchUserReq {
    private int userId;
    private String userName;
    private String phoneNum;
    private String agreeToReceiveMail;
    private String agreeToReceiveSms;
}
