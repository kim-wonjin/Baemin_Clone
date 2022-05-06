package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Text;

@Getter
@Setter
@AllArgsConstructor
public class PostUserAddressReq {
    private int userId;
    private String address;
    private String detailAddress;
    private String AddressName;
    private String isDefault;
}
