package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PatchAddressReq {
    private int addressId;
    private int userId;
    private String address;
    private String detailAddress;
    private String addressName;
    private String isDefault;
}
