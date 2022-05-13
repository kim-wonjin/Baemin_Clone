package com.example.demo.src.store.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetMenuOptionRes {
    private int optionId;
    private String optionName;
    private int valueId;
    private String valueName;
    private int additionalPrice;
}
