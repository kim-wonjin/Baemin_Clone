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
                        rs.getTimestamp("createdAt"),
                        rs.getTimestamp("expireAt")),
                getUserCouponParams);
    }

    public int createUser(PostUserReq postUserReq){
        String createUserQuery = "insert into Users (user_name, user_phone_number, user_email, user_password, agree_to_receive_mail, agree_to_receive_sms) VALUES (?,?,?,?,?,?)";
        Object[] createUserParams = new Object[]{ postUserReq.getUserName(), postUserReq.getPhoneNum(), postUserReq.getEmail(),
                postUserReq.getPassword(), postUserReq.getAgree_to_receive_mail(), postUserReq.getAgree_to_receive_sms()};
        this.jdbcTemplate.update(createUserQuery,createUserParams);
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

    public int modifyUser(PatchUserReq patchUserReq){
        String modifyUserQuery = "update Users set user_name = ?, user_phone_number = ?, agree_to_receive_mail = ?, agree_to_receive_sms = ? where user_id = ? ";
        Object[] modifyUserParams = new Object[]{patchUserReq.getUserName(), patchUserReq.getPhoneNum(), patchUserReq.getAgree_to_receive_mail(),
                patchUserReq.getAgree_to_receive_sms(), patchUserReq.getUserId()};

        return this.jdbcTemplate.update(modifyUserQuery,modifyUserParams);
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
