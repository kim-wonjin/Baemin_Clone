package com.example.demo.src.review;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.review.model.GetStoreReviewRes;
import com.example.demo.src.review.model.GetUserReviewRes;
import com.example.demo.src.store.model.*;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class ReviewProvider {

    private final ReviewDao reviewDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ReviewProvider(ReviewDao reviewDao, JwtService jwtService) {
        this.reviewDao = reviewDao;
        this.jwtService = jwtService;
    }

    public List<GetStoreReviewRes> getStoreReview(int storeId) throws BaseException {
        try {
            List<GetStoreReviewRes> getStoreReviewRes = reviewDao.getStoreReview(storeId);
            return getStoreReviewRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetUserReviewRes> getUserReview(int userId) throws BaseException {
        try {
            List<GetUserReviewRes> getUserReviewRes = reviewDao.getUserReview(userId);
            return getUserReviewRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}