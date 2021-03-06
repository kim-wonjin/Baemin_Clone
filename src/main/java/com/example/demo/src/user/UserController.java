package com.example.demo.src.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/api/users")
public class UserController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final UserProvider userProvider;
    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService;

    public UserController(UserProvider userProvider, UserService userService, JwtService jwtService){
        this.userProvider = userProvider;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    /*
     * 회원 조회 API
     * [GET] /users
     * 회원 번호 및 이메일 검색 조회 API
     * [GET] /users? Email=
     * @return BaseResponse<List<GetUserRes>>
     */
    //Query String
    /*
    @ResponseBody
    @GetMapping("") // (GET) 127.0.0.1:9000/api/users
    public BaseResponse<List<GetUserRes>> getUsers(@RequestParam(required = false) String Email) {
        try{
            if(Email == null){
                List<GetUserRes> getUsersRes = userProvider.getUsers();
                return new BaseResponse<>(getUsersRes);
            }
            // Get Users
            List<GetUserRes> getUsersRes = userProvider.getUsersByEmail(Email);
            return new BaseResponse<>(getUsersRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
*/
    /**
     * 유저 1명 조회 API
     * [GET] /users/:userId
     * @return BaseResponse<GetUserRes>
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/{userId}") // (GET) 127.0.0.1:9000/api/users/:userId
    public BaseResponse<GetUserRes> getUser(@PathVariable("userId") int userId) {
        // Get Users
        try{
            GetUserRes getUserRes = userProvider.getUser(userId);
            return new BaseResponse<>(getUserRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 유저 주소 조회 API
     * [GET] /users/:userId/address
     * @return BaseResponse<GetUserAddressRes>
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/{userId}/address") // (GET) 127.0.0.1:9000/api/users/:userId/address
    public BaseResponse<List <GetUserAddressRes>> getUserAddress(@PathVariable("userId") int userId) {
        // Get User Address
        try{
            //jwt에서 idx 추출.
            int userIdByJwt = jwtService.getUserId();
            //userId와 접근한 유저가 같은지 확인
            if(userId != userIdByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List <GetUserAddressRes> getUserAddressRes = userProvider.getUserAddress(userId);
            return new BaseResponse<>(getUserAddressRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 유저 쿠폰 조회 API
     * [GET] /users/:userId/coupons
     * @return BaseResponse<GetUserCouponRes>
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/{userId}/coupons") // (GET) 127.0.0.1:9000/api/users/:userId/coupons
    public BaseResponse<List <GetUserCouponRes>> getUserCoupon(@PathVariable("userId") int userId) {
        // Get User Address
        try{
            //jwt에서 idx 추출.
            int userIdByJwt = jwtService.getUserId();
            //userId와 접근한 유저가 같은지 확인
            if(userId != userIdByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List <GetUserCouponRes> getUserCouponRes = userProvider.getUserCoupon(userId);
            return new BaseResponse<>(getUserCouponRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 회원가입 API
     * [POST] /users
     * @return BaseResponse<PostUserRes>
     */
    // Body
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostUserRes> createUser(@RequestBody PostUserReq postUserReq) {
        // null validation
        if(postUserReq.getUserName() == null){
            return new BaseResponse<>(POST_USERS_EMPTY_USER_NAME);
        }
        if(postUserReq.getPhoneNum() == null){
            return new BaseResponse<>(POST_USERS_EMPTY_PHONE_NUM);
        }
        if(postUserReq.getPassword() == null){
            return new BaseResponse<>(POST_USERS_EMPTY_PASSWORD);
        }
        if(postUserReq.getEmail() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
        }
        //이메일 정규표현
        if(!isRegexEmail(postUserReq.getEmail())){
            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
        }
        try{
            PostUserRes postUserRes = userService.createUser(postUserReq);
            return new BaseResponse<>(postUserRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     * 로그인 API
     * [POST] /users/login
     * @return BaseResponse<PostLoginRes>
     */
    @ResponseBody
    @PostMapping("/login")
    public BaseResponse<PostLoginRes> logIn(@RequestBody PostLoginReq postLoginReq){
        if(postLoginReq.getEmail() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
        }
        if(postLoginReq.getPassword() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_PASSWORD);
        }
        if(!isRegexEmail(postLoginReq.getEmail())){
            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
        }
        try{
            // TODO: 유저의 status ex) 비활성화된 유저, 탈퇴한 유저 등을 관리해주고 있다면 해당 부분에 대한 validation 처리도 해주셔야합니다.
            PostLoginRes postLoginRes = userProvider.login(postLoginReq);
            return new BaseResponse<>(postLoginRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 유저 주소 생성 API
     * [POST] /users/address
     * @return BaseResponse<PostUserAddressRes>
     */
    // Body
    @ResponseBody
    @PostMapping("/address")
    public BaseResponse<PostUserAddressRes> createUser(@RequestBody PostUserAddressReq postUserAddressReq) {
        try{
            //jwt에서 idx 추출.
            int userIdByJwt = jwtService.getUserId();
            //userId와 접근한 유저가 같은지 확인
            if(postUserAddressReq.getUserId() != userIdByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            // null validation
            if(postUserAddressReq.getAddress() == null){
                return new BaseResponse<>(POST_USERS_EMPTY_ADDRESS);
            }
            PostUserAddressRes postUserAddressRes = userService.createAddress(postUserAddressReq);
            return new BaseResponse<>(postUserAddressRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 유저 쿠폰 생성 API
     * [POST] /users/coupons
     * @return BaseResponse<PostUserAddressRes>
     */
    // Body
    @ResponseBody
    @PostMapping("/coupons")
    public BaseResponse<PostUserCouponRes> createUser(@RequestBody PostUserCouponReq postUserCouponReq) {
        try{
            //jwt에서 idx 추출.
            int userIdByJwt = jwtService.getUserId();
            //userId와 접근한 유저가 같은지 확인
            if(postUserCouponReq.getUserId() != userIdByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            PostUserCouponRes postUserCouponRes= userService.createCoupon(postUserCouponReq);
            return new BaseResponse<>(postUserCouponRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 유저정보 수정 API
     * [PATCH] /users/:userId
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/{userId}")
    public BaseResponse<String> modifyUser(@PathVariable("userId") int userId, @RequestBody PatchUserReq patchUserReq){
        try {
            //jwt에서 idx 추출.
            int userIdByJwt = jwtService.getUserId();
            //userId와 접근한 유저가 같은지 확인
            if(userId != userIdByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            //같다면 유저정보 수정
            userService.modifyUser(patchUserReq);

            String result = "변경 완료";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 유저주소 수정 API
     * [PATCH] /users/address/:addressId
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/address/{addressId}")
    public BaseResponse<String> modifyAddress(@PathVariable("addressId") int addressId, @RequestBody PatchAddressReq patchAddressReq ){
        try {
            //jwt에서 idx 추출.
            int userIdByJwt = jwtService.getUserId();
            //userId와 접근한 유저가 같은지 확인
            if(patchAddressReq.getUserId() != userIdByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            //같다면 유저 주소 수정
            userService.modifyAddress(patchAddressReq);

            String result = "변경 완료";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 유저주소 삭제 API
     * [DELETE] /users/:userId/address/:addressId
     * @return BaseResponse<String>
     */
    @ResponseBody
    @DeleteMapping("/{userId}/address/{addressId}")
    public BaseResponse<String> deleteAddress(@PathVariable("userId") int userId, @PathVariable("addressId") int addressId){
        try {
            //jwt에서 idx 추출.
            int userIdByJwt = jwtService.getUserId();
            //userId와 접근한 유저가 같은지 확인
            if(userId != userIdByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            //같다면 유저 주소 삭제
            userService.deleteAddress(addressId);

            String result = "삭제 완료";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    @ResponseBody
    @GetMapping("/kakao")
    public BaseResponse<PostKakaoRes> kakaoCallback(@RequestParam String code) throws BaseException {

        String access_Token = userService.getKaKaoAccessToken(code);
        return userService.createKakaoUser(access_Token);

    }

}
