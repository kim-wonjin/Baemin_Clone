package com.example.demo.src.review;

import com.example.demo.src.review.model.GetStoreReviewRes;
import com.example.demo.src.review.model.GetUserReviewRes;
import com.example.demo.src.store.model.*;
import com.example.demo.src.user.model.GetUserAddressRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;
@Repository
public class ReviewDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetStoreReviewRes> getStoreReview(int storeId){
        String getStoreReviewQuery = "SELECT * FROM Review WHERE store_id = ?";
        int getStoreReviewParams = storeId;
        return this.jdbcTemplate.query(getStoreReviewQuery,
                (rs, rowNum) -> new GetStoreReviewRes(
                        rs.getInt("review_id"),
                        rs.getInt("order_id"),
                        rs.getInt("user_id"),
                        rs.getFloat("rating"),
                        rs.getString("review_text"),
                        rs.getString("owner_review_comment")),
                getStoreReviewParams);
    }
    public List<GetUserReviewRes> getUserReview(int userId){
        String getUserReviewQuery = "SELECT * FROM Review WHERE user_id = ?";
        int getUserReviewParams = userId;
        return this.jdbcTemplate.query(getUserReviewQuery,
                (rs, rowNum) -> new GetUserReviewRes(
                        rs.getInt("review_id"),
                        rs.getInt("order_id"),
                        rs.getInt("store_id"),
                        rs.getFloat("rating"),
                        rs.getString("review_text"),
                        rs.getString("owner_review_comment")),
                getUserReviewParams);
    }
}
