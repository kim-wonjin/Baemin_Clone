package com.example.demo.src.order;

import com.example.demo.src.order.model.GetCartRes;
import com.example.demo.src.order.model.PostOrderMenuReq;
import com.example.demo.src.order.model.PostOrderReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final OrderProvider orderProvider;
    @Autowired
    private final OrderService orderService;
    @Autowired
    private final JwtService jwtService;

    public OrderController(OrderProvider orderProvider, OrderService orderService, JwtService jwtService) {
        this.orderProvider = orderProvider;
        this.orderService = orderService;
        this.jwtService = jwtService;
    }

    /**
     * 주문 메뉴 생성 API
     * [POST] /order/carts/:userId/menu
     * @return BaseResponse<String>
     */
    // Body
    @ResponseBody
    @PostMapping("/carts/{userId}/menu")
    public BaseResponse<String> createOrderMenu(@PathVariable ("userId") int userId, @RequestBody PostOrderMenuReq postOrderMenuReq) {
        try{
            //jwt에서 idx 추출.
            int userIdByJwt = jwtService.getUserId();
            //userId와 접근한 유저가 같은지 확인
            if((userId) != userIdByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            orderService.createOrderMenu(userId, postOrderMenuReq);
            String result = "장바구니에 메뉴 추가 성공";
            return new BaseResponse<>(result);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 주문 메뉴 삭제 API
     * [DELETE] /order/carts/:userId/menu/:menuId
     * @return BaseResponse<String>
     */
    // Body
    @ResponseBody
    @DeleteMapping("/carts/{userId}/menu/{orderMenuId}")
    public BaseResponse<String> deleteOrderMenu(@PathVariable("userId") int userId, @PathVariable("orderMenuId") int orderMenuId){
        try {
            //jwt에서 idx 추출.
            int userIdByJwt = jwtService.getUserId();
            //userId와 접근한 유저가 같은지 확인
            if(userId != userIdByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            //같다면 주문 메뉴 삭제
            orderService.deleteOrderMenu(userId, orderMenuId);

            String result = "삭제 완료";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 장바구니 조회 API
     * [GET] /carts/:userId
     * @return BaseResponse<GetCartRes>
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/carts/{userId}") // (GET) 127.0.0.1:9000/api/order/carts/:userId
    public BaseResponse<GetCartRes> getUserAddress(@PathVariable("userId") int userId) {
        // Get User cart
        try{
            //jwt에서 idx 추출.
            int userIdByJwt = jwtService.getUserId();
            //userId와 접근한 유저가 같은지 확인
            if(userId != userIdByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            GetCartRes getCartRes = orderProvider.getCart(userId);
            return new BaseResponse<>(getCartRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 주문 생성 API
     * [POST] /order/:userId
     * @return BaseResponse<POST>
     */
    // Body
    @ResponseBody
    @PostMapping("{userId}")
    public BaseResponse<Integer> createOrder(@PathVariable ("userId") int userId, @RequestBody PostOrderReq postOrderReq) {
        try{
            //jwt에서 idx 추출.
            int userIdByJwt = jwtService.getUserId();
            //userId와 접근한 유저가 같은지 확인
            if((userId) != userIdByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            Integer orderId = orderService.createOrder(userId, postOrderReq);
            return new BaseResponse<>(orderId);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}