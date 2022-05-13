package com.example.demo.src.order;

import com.example.demo.config.BaseException;
import com.example.demo.src.order.model.PostOrderMenuReq;
import com.example.demo.src.order.model.PostOrderReq;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class OrderService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final OrderDao orderDao;
    private final OrderProvider orderProvider;
    private final JwtService jwtService;


    @Autowired
    public OrderService(OrderDao orderDao, OrderProvider orderProvider, JwtService jwtService) {
        this.orderDao = orderDao;
        this.orderProvider = orderProvider;
        this.jwtService = jwtService;
    }

    @Transactional (rollbackOn = BaseException.class)
    public void createOrderMenu(int userId, PostOrderMenuReq postOrderMenuReq) throws BaseException {
        // 담을 카트 아이디 찾기
        int cartId;
        try{
            cartId = orderProvider.checkCart(userId);
        } catch (Exception exception) {
            try{
                // 처음 메뉴 담는 경우 -> 장바구니 새로 생성
                cartId = orderDao.createCart(userId, postOrderMenuReq.getMenuId());
            } catch (Exception exception1) {
                throw new BaseException(POST_ORDER_NO_CART);
            }
        }
        // 주문 메뉴 생성
        try{
            int result = orderDao.createOrderMenu(postOrderMenuReq, cartId);
            if(result == 0){
                throw new BaseException(CREATE_FAIL_ORDER_MENU);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
        // 장바구니 총 금액 / 예상금액 추가
        try{
            orderDao.plusCartPrice(postOrderMenuReq, cartId);
        } catch (Exception exception) {
            throw new BaseException(UPDATE_CART_PRICE_FAIL);
        }

    }

    @Transactional (rollbackOn = BaseException.class)
    public void deleteOrderMenu(int userId, int orderMenuId) throws BaseException {
        // 장바구니 총 금액 / 예상금액 감소
        try{
            int cartId = orderDao.checkCart(userId);
            orderDao.minusCartPrice(cartId, orderMenuId);
        } catch (Exception exception) {
            throw new BaseException(UPDATE_CART_PRICE_FAIL);
        }
        // 주문 메뉴 삭제
        try{
            int result = orderDao.deleteOrderMenu(orderMenuId);
            if(result == 0){
                throw new BaseException(DELETE_FAIL_ORDER_MENU);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional (rollbackOn = BaseException.class)
    public int createOrder(int userId, PostOrderReq postOrderReq) throws BaseException {
        int cartId;
        int orderId;
        // 유저의 장바구니 확인
        try{
            cartId = orderDao.checkCart(userId);
        } catch (Exception exception) {
            throw new BaseException(POST_ORDER_NO_CART);
        }
        // 장바구니 주문
        try{
            orderId = orderDao.createOrder(userId, cartId, postOrderReq);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
        // 장바구니 상태변경
        try{
            orderDao.updateCartStatus(cartId);
        } catch (Exception exception) {
            throw new BaseException(UPDATE_CART_STATUS_FAIL);
        }
        // 가게 주문 수 증가
        try{
            orderDao.increaseOrderCount(cartId);
        } catch (Exception exception) {
            throw new BaseException(UPDATE_ORDER_COUNT_FAIL);
        }
        return (orderId);

    }
}
