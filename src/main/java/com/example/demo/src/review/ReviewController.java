package com.example.demo.src.review;

import com.example.demo.src.review.model.GetStoreReviewRes;
import com.example.demo.src.review.model.GetUserReviewRes;
import com.example.demo.src.store.model.*;
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
@RequestMapping("/api/review")
public class ReviewController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final ReviewProvider reviewProvider;
    @Autowired
    private final ReviewService reviewService;
    @Autowired
    private final JwtService jwtService;

    public ReviewController(ReviewProvider reviewProvider, ReviewService reviewService, JwtService jwtService) {
        this.reviewProvider = reviewProvider;
        this.reviewService = reviewService;
        this.jwtService = jwtService;
    }

    /**
     * 가게 리뷰 조회 API
     * [GET] /api/review/:storeId
     * @return BaseResponse<GetStoreReviewRes>
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/{storeId}") // (GET) 127.0.0.1:9000/api/review/:storeId
    public BaseResponse<List <GetStoreReviewRes>> getStoreReview(@PathVariable("storeId") int storeId) {
        // Get Store Review
        try{
            List <GetStoreReviewRes> getStoreReviewRes = reviewProvider.getStoreReview(storeId);
            return new BaseResponse<>(getStoreReviewRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 유저 리뷰 조회 API
     * [GET] /api/review/user/:userId
     * @return BaseResponse<GetStoreReviewRes>
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/user/{userId}") // (GET) 127.0.0.1:9000/api/review/:userId
    public BaseResponse<List <GetUserReviewRes>> getUserReview(@PathVariable("userId") int userId) {
        // Get User Review
        try{
            //jwt에서 idx 추출.
            int userIdByJwt = jwtService.getUserId();
            //userId와 접근한 유저가 같은지 확인
            if(userId != userIdByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List <GetUserReviewRes> getUserReviewRes = reviewProvider.getUserReview(userId);
            return new BaseResponse<>(getUserReviewRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}