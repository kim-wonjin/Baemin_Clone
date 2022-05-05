package com.example.demo.src.store;

import com.example.demo.src.store.model.GetStoreCategoriesRes;
import com.example.demo.src.store.model.GetStoreRes;
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
@RequestMapping("/api/stores")
public class StoreController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final StoreProvider storeProvider;
    @Autowired
    private final StoreService storeService;
    @Autowired
    private final JwtService jwtService;

    public StoreController(StoreProvider storeProvider, StoreService storeService, JwtService jwtService) {
        this.storeProvider = storeProvider;
        this.storeService = storeService;
        this.jwtService = jwtService;
    }

    /**
     * 가게 카테고리 목록 조회 API
     * [GET] /stores/categories
     * * @return BaseResponse<GetStoreCategoriesRes>
     */
    @ResponseBody
    @GetMapping("/categories") // (GET) 127.0.0.1:9000/api/stores/categories
    public BaseResponse<List <GetStoreCategoriesRes>> getStoreCategoriesRes() {
        // Get Store categories
        try{
            List <GetStoreCategoriesRes> getStoreCategoriesRes = storeProvider.getStoreCategories();
            return new BaseResponse<>(getStoreCategoriesRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 특정 가게 조회 API
     * [GET] /stores/:storeId
     * * @return BaseResponse<GetStoreRes>
     */
    @ResponseBody
    @GetMapping("/{storeId}") // (GET) 127.0.0.1:9000/api/stores/:storeId
    public BaseResponse<GetStoreRes> getStoreRes(@PathVariable("storeId") int storeId) {
        // Get Store
        try{
            GetStoreRes getStoreRes = storeProvider.getStore(storeId);
            return new BaseResponse<>(getStoreRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}