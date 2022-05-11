package com.example.demo.src.user;


import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
/*
    public List<GetUserRes> getUsers(){
        String getUsersQuery = "select * from UserInfo";
        return this.jdbcTemplate.query(getUsersQuery,
                (rs,rowNum) -> new GetUserRes(
                        rs.getInt("userIdx"),
                        rs.getString("userName"),
                        rs.getString("ID"),
                        rs.getString("Email"),
                        rs.getString("password"))
        );
    }

    public List<GetUserRes> getUsersByEmail(String email){
        String getUsersByEmailQuery = "select * from UserInfo where email =?";
        String getUsersByEmailParams = email;
        return this.jdbcTemplate.query(getUsersByEmailQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("userIdx"),
                        rs.getString("userName"),
                        rs.getString("ID"),
                        rs.getString("Email"),
                        rs.getString("password")),
                getUsersByEmailParams);
    }
*/
    public GetUserRes getUser(int userId){
        String getUserQuery = "select * from Users where user_id = ?";
        int getUserParams = userId;
        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("user_id"),
                        rs.getString("user_name"),
                        rs.getString("user_phone_number"),
                        rs.getString("user_email"),
                        rs.getString("user_password"),
                        rs.getString("agree_to_receive_mail"),
                        rs.getString("agree_to_receive_sms")),
                getUserParams);
    }

    public List <GetUserAddressRes> getUserAddress(int userId){
        String getUserAddressQuery = "select * from User_address where user_id = ? && status = 'ACTIVE'";
        int getUserAddressParams = userId;
        return this.jdbcTemplate.query(getUserAddressQuery,
                (rs, rowNum) -> new GetUserAddressRes(
                        rs.getInt("address_id"),
                        rs.getString("address"),
                        rs.getString("detail_address"),
                        rs.getString("address_name"),
                        rs.getString("is_default_address")),
                getUserAddressParams);
    }

    public List <GetUserCouponRes> getUserCoupon(int userId){
        String getUserCouponQuery = "SELECT C.discount_price AS discount,\n" +
                "       C.coupon_name AS couponName, C.min_price AS minPrice,\n" +
                "       C.created_at AS createdAt,\n" +
                "       C.expire_at AS expireAt \n" +
                "FROM Coupons C, User_coupons U \n" +
                "WHERE C.coupon_id = U.coupon_id && U.user_id = ?";
        int getUserCouponParams = userId;
        return this.jdbcTemplate.query(getUserCouponQuery,
                (rs, rowNum) -> new GetUserCouponRes(
                        rs.getString("couponName"),
                        rs.getInt("discount"),
                        rs.getInt("minPrice"),
                        rs.getString("createdAt"),
                        rs.getString("expireAt")),
                getUserCouponParams);
    }

    public int createUser(PostUserReq postUserReq){
        String createUserQuery = "insert into Users (user_name, user_phone_number, user_email, user_password, agree_to_receive_mail, agree_to_receive_sms) VALUES (?,?,?,?,?,?)";
        Object[] createUserParams = new Object[]{ postUserReq.getUserName(), postUserReq.getPhoneNum(), postUserReq.getEmail(),
                postUserReq.getPassword(), postUserReq.getAgreeToReceiveMail(), postUserReq.getAgreeToReceiveSms()};
        this.jdbcTemplate.update(createUserQuery,createUserParams);
        String lastInsertIdQuery = "SELECT last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

    public int createAddress(PostUserAddressReq postUserAddressReq){
        String createAddressQuery = "insert into User_address (user_id, address, detail_address, address_name, is_default_address) VALUES (?,?,?,?,?)";
        Object[] createAddressParams = new Object[]{postUserAddressReq.getUserId(), postUserAddressReq.getAddress(), postUserAddressReq.getDetailAddress(),
                postUserAddressReq.getAddressName(), postUserAddressReq.getIsDefault()};
        this.jdbcTemplate.update(createAddressQuery,createAddressParams);
        String lastInsertIdQuery = "SELECT last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

    public int createCoupon(PostUserCouponReq postUserCouponReq){
        String createCouponQuery = "insert into User_coupons (user_id, coupon_id) VALUES (?,?)";
        Object[] createCouponParams = new Object[]{postUserCouponReq.getUserId(), postUserCouponReq.getCouponId() };
        this.jdbcTemplate.update(createCouponQuery,createCouponParams);
        String lastInsertIdQuery = "SELECT last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

    public int checkEmail(String email){
        String checkEmailQuery = "select exists(select user_email from Users where user_email = ?)";
        String checkEmailParams = email;
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams);
    }

    public int checkCoupon(int userId, int couponId){
        String checkCouponQuery = "select exists(select user_coupon_id from User_coupons where user_id = ? && coupon_id = ?)";
        Object[] checkCouponParams = new Object[]{userId, couponId};
        return this.jdbcTemplate.queryForObject(checkCouponQuery,
                int.class,
                checkCouponParams);
    }

    public int modifyUser(PatchUserReq patchUserReq){
        String modifyUserQuery = "update Users set user_name = ?, user_phone_number = ?, agree_to_receive_mail = ?, agree_to_receive_sms = ? where user_id = ? ";
        Object[] modifyUserParams = new Object[]{patchUserReq.getUserName(), patchUserReq.getPhoneNum(), patchUserReq.getAgreeToReceiveMail(),
                patchUserReq.getAgreeToReceiveSms(), patchUserReq.getUserId()};

        return this.jdbcTemplate.update(modifyUserQuery,modifyUserParams);
    }

    public int modifyAddress(PatchAddressReq patchAddressReq){
        String modifyAddressQuery = "update User_address set address = ?, detail_address = ?, address_name = ?, is_default_address = ?";
        Object[] modifyAddressParams = new Object[]{patchAddressReq.getAddress(), patchAddressReq.getDetailAddress(), patchAddressReq.getAddressName(), patchAddressReq.getIsDefault()};

        return this.jdbcTemplate.update(modifyAddressQuery,modifyAddressParams);
    }

    public int deleteAddress(int addressId){
        String deleteAddressQuery = "DELETE FROM User_address WHERE address_id = ?";
        int deleteAddressParams = addressId;

        return this.jdbcTemplate.update(deleteAddressQuery, deleteAddressParams);
    }

    public User getPwd(PostLoginReq postLoginReq){
        String getPwdQuery = "select user_id, user_password, user_email, user_name from Users where user_email = ?";
        String getPwdParams = postLoginReq.getEmail();

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs,rowNum)-> new User(
                        rs.getInt("user_id"),
                        rs.getString("user_email"),
                        rs.getString("user_name"),
                        rs.getString("user_password")
                ),
                getPwdParams
        );
    }


}
