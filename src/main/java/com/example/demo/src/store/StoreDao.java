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

    public List<GetStoreByCategoryRes> getStoreByCategory(int categoryId){
        String getStoreByCategoryQuery = "SELECT S.store_id AS storeId, S.store_name AS storeName, I.store_image_url AS storeImageUrl, S.average_rating AS rating, S.store_description AS description,\n" +
                "       S.min_price AS minPrice,S.delivery_fee AS deliveryFee, CONCAT(S.min_required_time, '~' ,S.max_required_time) AS requiredTime,\n" +
                "       S.is_takeout_available AS isTakeoutPossible FROM Stores S LEFT JOIN Store_images I ON S.store_id = I.store_id\n" +
                "WHERE S.store_category_id = ?";
        return this.jdbcTemplate.query(getStoreByCategoryQuery,
                (rs, rowNum) -> new GetStoreByCategoryRes(
                        rs.getInt("storeId"),
                        rs.getString("storeName"),
                        rs.getString("storeImageurl"),
                        rs.getFloat("rating"),
                        rs.getString("description"),
                        rs.getInt("minPrice"),
                        rs.getInt("deliveryFee"),
                        rs.getString("requiredTime"),
                        rs.getString("isTakeoutPossible")), categoryId);
    }

    public List<GetStoreMenuRes> getStoreMenu(int storeId){
        String getStoreMenuQuery = "SELECT M.menu_id AS menuId, M.menu_name AS menuName, M.menu_description AS description, M.menu_price AS price,\n" +
                " M.is_signature_menu AS isSignature, M.is_popular_menu AS isPopular, I.menu_image_url AS menuImageUrl, M.menu_category_id AS categoryId\n" +
                "FROM Menu M, Menu_images I\n" +
                "WHERE M.menu_id = I.menu_id && M.store_id = ?";
        return this.jdbcTemplate.query(getStoreMenuQuery,
                (rs, rowNum) -> new GetStoreMenuRes(
                        rs.getInt("menuId"),
                        rs.getString("menuName"),
                        rs.getString("description"),
                        rs.getInt("price"),
                        rs.getString("isSignature"),
                        rs.getString("isPopular"),
                        rs.getString("menuImageUrl"),
                        rs.getInt("categoryId")), storeId);
    }

    public List<GetMenuOptionRes> getMenuOption(int menuId){
        String getMenuOptionQuery = "SELECT O.option_id AS optionId, O.option_name AS optionName, V.option_value_id AS valueId, V.option_value_name AS valueName, V.additional_price AS additionalPrice \n" +
                " FROM Menu_options O JOIN Menu_options_value V ON O.option_id = V.option_id WHERE O.menu_id = ? ";
        return this.jdbcTemplate.query(getMenuOptionQuery,
                        (rs, rowNum) -> new GetMenuOptionRes(
                                rs.getInt("optionId"),
                                rs.getString("optionName"),
                                rs.getInt("valueId"),
                                rs.getString("valueName"),
                                rs.getInt("additionalPrice")
                        ), menuId);

    }
}
