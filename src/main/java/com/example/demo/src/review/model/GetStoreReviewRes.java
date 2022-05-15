package com.example.demo.src.review.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetStoreReviewRes {
    private int reviewId;
    private int orderId;
    private int userId;
    private float rating;
    private String text;
    private String ownerComment;
}
