package com.example.demo.src.store.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetStoreMenuRes {
    private int menuId;
    private String menuName;
    private String description;
    private int price;
    private String isSignature;
    private String isPopular;
    private String menuImageUrl;
    private int categoryId;
}
