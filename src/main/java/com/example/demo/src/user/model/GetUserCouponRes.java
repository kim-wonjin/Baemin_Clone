package com.example.demo.src.user.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class GetUserCouponRes {
    private String couponName;
    private int discount;
    private int minPrice;
    private String createdAt;
    private String expireAt;
}