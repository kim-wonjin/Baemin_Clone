package com.example.demo.src.store.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetStoreByCategoryRes {
    private int storeId;
    private String storeName;
    private String storeImageUrl;
    private float rating;
    private String description;
    private int minPrice;
    private int deliveryFee;
    private String requiredTime;
    private String isTakeoutPossible;
}
