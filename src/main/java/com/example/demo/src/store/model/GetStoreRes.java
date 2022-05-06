package com.example.demo.src.store.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.jpa.internal.AfterCompletionActionLegacyJpaImpl;

@Getter
@Setter
@AllArgsConstructor
public class GetStoreRes {
    private int storeId;
    private String storeName;
    private String storeTelephone;
    private String isOpen;
    private int minPrice;
    private String isTakeoutAvailable;
    private String isBaemin1;
    private String address;
    private float distance;
    private int minRequiredTime;
    private int maxRequiredTime;
    private float rating;
    private int likes;
    private int orders;
    private String description;
}
