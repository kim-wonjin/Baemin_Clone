package com.example.demo.src.order;

import com.example.demo.src.order.model.GetCartRes;
import com.example.demo.src.order.model.PostOrderMenuReq;
import com.example.demo.src.order.model.PostOrderReq;
import com.example.demo.src.user.model.*;
import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

@Repository
public class OrderDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) { this.jdbcTemplate = new JdbcTemplate(dataSource); }

    public int checkCart(int userId){
        String checkCartQuery = "SELECT C.cart_id AS cartId FROM Order_carts C WHERE C.user_id = ? && C.status = 'ACTIVE'";
        int checkCartParams = userId;
        return this.jdbcTemplate.queryForObject(checkCartQuery, int.class, checkCartParams);
    }

    public int createCart(int userId, int menuId){
        String getDeliveryFeeQuery = "SELECT S.delivery_fee FROM Stores S, Menu M WHERE M.menu_id = ? && M.store_id = S.store_id";
        int deliveryFee = this.jdbcTemplate.queryForObject(getDeliveryFeeQuery, int.class, menuId);
        String createCartQuery = "INSERT INTO Order_carts (user_id, delivery_fee, expected_price) VALUES (?,?,?)";
        Object[] createCartParams = new Object[] {userId, deliveryFee, deliveryFee};
        return this.jdbcTemplate.update(createCartQuery, createCartParams);
    }

    public void plusCartPrice(PostOrderMenuReq postOrderMenuReq, int cartId){
        String getMenuPriceQuery = "SELECT M.menu_price FROM Menu M WHERE M.menu_id = ?";
        int menuPrice = this.jdbcTemplate.queryForObject(getMenuPriceQuery, int.class, postOrderMenuReq.getMenuId());
        String plusCartPriceQuery = "UPDATE Order_carts\n" +
                "SET total_price = Order_carts.total_price + ?, expected_price = Order_carts.expected_price + ?\n" +
                "WHERE Order_carts.cart_id = ?";
        Object[] plusCartPriceParams = new Object[] {menuPrice * postOrderMenuReq.getQuantity(), menuPrice * postOrderMenuReq.getQuantity(),cartId};
        this.jdbcTemplate.update(plusCartPriceQuery,plusCartPriceParams);
    }

    public void minusCartPrice(int cartId, int orderMenuId){
        String getMenuPriceQuery = "SELECT M.menu_price FROM Menu M, Order_menu O WHERE O.order_menu_id = ? && O.menu_id = M.menu_id";
        int menuPrice = this.jdbcTemplate.queryForObject(getMenuPriceQuery, int.class, orderMenuId);
        String minusCartPriceQuery = "UPDATE Order_carts\n" +
                "SET total_price = Order_carts.total_price - ?, expected_price = Order_carts.expected_price - ?\n" +
                "WHERE Order_carts.cart_id = ?";
        Object[] minusCartPriceParams = new Object[] {menuPrice, menuPrice, cartId};
        this.jdbcTemplate.update(minusCartPriceQuery, minusCartPriceParams);
    }

    public int createOrderMenu(PostOrderMenuReq postOrderMenuReq, int cartId){
        String createOrderMenuQuery = "INSERT INTO Order_menu (menu_id, cart_id, order_menu_quantity) VALUES (?,?,?)";
        Object[] createOrderMenuParams = new Object[] { postOrderMenuReq.getMenuId(), cartId, postOrderMenuReq.getQuantity() };
        return this.jdbcTemplate.update(createOrderMenuQuery, createOrderMenuParams);
    }

    public int deleteOrderMenu(int orderMenuId){
        String deleteOrderMenuQuery = "DELETE FROM Order_menu WHERE order_menu_id = ?";
        int deleteOrderMenuParams = orderMenuId;
        return this.jdbcTemplate.update(deleteOrderMenuQuery, deleteOrderMenuParams);
    }

    public GetCartRes getCart(int userId){
        String getCartQuery = "SELECT O.cart_id, O.user_id, O.total_price, O.delivery_fee, O.expected_price FROM Order_carts O WHERE O.user_id = ?";
        return this.jdbcTemplate.queryForObject(getCartQuery,
                (rs, rowNum) -> new GetCartRes(
                        rs.getInt("cart_id"),
                        rs.getInt("user_id"),
                        rs.getInt("total_price"),
                        rs.getInt("delivery_fee"),
                        rs.getInt("expected_price"))
                , userId);
    }

    public int createOrder(int userId, int cartId, PostOrderReq postOrderReq){
        String getStoreIdQuery = "SELECT M.store_id FROM Menu M, Order_menu O, Order_carts C WHERE C.cart_id = O.cart_id && O.menu_id = M.menu_id && C.cart_id = ?";
        String getFinalPriceQuery = "SELECT C.expected_price FROM Order_carts C WHERE C.cart_id = ?";
        int storeId = this.jdbcTemplate.queryForObject(getStoreIdQuery, int.class, cartId);
        int finalPrice = this.jdbcTemplate.queryForObject(getFinalPriceQuery, int.class, cartId);

        String createOrderQuery = "INSERT INTO Orders (cart_id, user_id, store_id, order_type, delivery_address_id,\n " +
                "final_price, to_owner_memo, to_rider_memo, user_coupon_id, is_disposable_needed, pay_type)\n" +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        Object[] creatOrderParams = new Object[] {cartId, userId, storeId, postOrderReq.getOrderType(), postOrderReq.getAddressId(), finalPrice, postOrderReq.getToStoreMemo(),
        postOrderReq.getToRiderMemo(), postOrderReq.getUserCouponId(), postOrderReq.getIsDisposableNeeded(), postOrderReq.getPayType()};
        this.jdbcTemplate.update(createOrderQuery, creatOrderParams);

        String lastInsertIdQuery = "SELECT last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

    public void updateCartStatus(int cartId){
        String updateCartStatusQuery = "UPDATE Order_carts SET status = '?????? ??????' WHERE Order_carts.cart_id = ?";
        this.jdbcTemplate.update(updateCartStatusQuery, cartId);
    }

    public void increaseOrderCount(int cartId){
        String getStoreIdQuery = "SELECT M.store_id FROM Menu M, Order_menu O, Order_carts C WHERE C.cart_id = O.cart_id && O.menu_id = M.menu_id && C.cart_id = ?";
        int storeId = this.jdbcTemplate.queryForObject(getStoreIdQuery, int.class, cartId);
        String increaseOrderCountQuery = "UPDATE Stores SET order_count = Stores.order_count + 1 WHERE Stores.store_id = ?";
        this.jdbcTemplate.update(increaseOrderCountQuery, storeId);
    }
}