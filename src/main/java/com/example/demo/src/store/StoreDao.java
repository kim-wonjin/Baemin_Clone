package com.example.demo.src.store;

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
public class StoreDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public List <GetStoreCategoriesRes> getStoreCategories(){
        String getStoreCategoriesQuery = "select * from Store_categories";
        return this.jdbcTemplate.query(getStoreCategoriesQuery,
                (rs, rowNum) -> new GetStoreCategoriesRes(
                        rs.getInt("store_category_id"),
                        rs.getString("store_category_name"))
        );
    }

    public GetStoreRes getStore(int storeId){
        String getStoreQuery = "SELECT * FROM Stores WHERE store_id = ?";
        int getStoreParams = storeId;
        return this.jdbcTemplate.queryForObject(getStoreQuery,
                (rs, rowNum) -> new GetStoreRes(
                        rs.getInt("store_id"),
                        rs.getString("store_name"),
                        rs.getString("store_telephone"),
                        rs.getString("is_open"),
                        rs.getInt("min_price"),
                        rs.getString("is_takeout_available"),
                        rs.getString("is_baemin1"),
                        rs.getString("store_address"),
                        rs.getFloat("distance"),
                        rs.getInt("min_required_time"),
                        rs.getInt("max_required_time"),
                        rs.getFloat("average_rating"),
                        rs.getInt("like_count"),
                        rs.getInt("order_count"),
                        rs.getString("store_description")),
                        getStoreParams);
    }
}
