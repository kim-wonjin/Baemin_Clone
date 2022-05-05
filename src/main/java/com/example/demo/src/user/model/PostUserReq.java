package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostUserReq {
    private String UserName;
    private String phoneNum;
    private String email;
    private String password;
    private String agree_to_receive_mail;
    private String agree_to_receive_sms;
}
