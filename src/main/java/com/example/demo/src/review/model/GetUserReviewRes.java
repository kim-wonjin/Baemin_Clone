package com.example.demo.src.review.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserReviewRes {
    private int reviewId;
    private int orderId;
    private int storeId;
    private float rating;
    private String text;
    private String ownerComment;
}