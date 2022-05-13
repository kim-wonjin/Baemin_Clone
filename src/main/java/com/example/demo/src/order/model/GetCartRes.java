package com.example.demo.src.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetCartRes {
    private int cartId;
    private int userId;
    private int totalPrice;
    private int deliveryFee;
    private int expectedPrice;
}
