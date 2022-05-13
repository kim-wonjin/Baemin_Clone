package com.example.demo.src.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostOrderReq {
    private String orderType;
    private int addressId;
    private String toStoreMemo;
    private String toRiderMemo;
    private int userCouponId;
    private String isDisposableNeeded;
    private String payType;
}
