package com.example.demo.src.store;

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
    public BaseResponse<List <GetStoreCategoriesRes>> getStoreCategories() {
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
    public BaseResponse<GetStoreRes> getStore(@PathVariable("storeId") int storeId) {
        // Get Store
        try{
            GetStoreRes getStoreRes = storeProvider.getStore(storeId);
            return new BaseResponse<>(getStoreRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 카테고리 별 가게 조회 API
     * [GET] /stores/?categoryId=
     * * @return BaseResponse<GetStoreByCategoryRes>
     */
    @ResponseBody
    @GetMapping("") // (GET) 127.0.0.1:9000/api/stores/?categoryId=
    public BaseResponse<List<GetStoreByCategoryRes>> getStoreByCategory(@RequestParam(required = true) int categoryId) {
        // Get Store By Category
        try{
            List <GetStoreByCategoryRes> getStoreByCategoryRes = storeProvider.getStoreByCategory(categoryId);
            return new BaseResponse<>(getStoreByCategoryRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 가게 메뉴 조회 API
     * [GET] /stores/:storeId/menu
     * * @return BaseResponse<GetStoreMenuRes>
     */
    @ResponseBody
    @GetMapping("/{storeId}/menu") // (GET) 127.0.0.1:9000/api/stores/?categoryId=
    public BaseResponse<List<GetStoreMenuRes>> getStoreMenu(@PathVariable ("storeId") int storeId) {
        // Get Store By Category
        try{
            List <GetStoreMenuRes> getStoreMenuRes = storeProvider.getStoreMenu(storeId);
            return new BaseResponse<>(getStoreMenuRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 가게 메뉴 옵션 조회 API
     * [GET] /stores/menu/:menuId/options
     * * @return BaseResponse<GetMenuOptionRes>
     */
    @ResponseBody
    @GetMapping("/menu/{menuId}/options") // (GET) 127.0.0.1:9000/api/stores/menu/:menuId/options
    public BaseResponse<List<GetMenuOptionRes>> getMenuOptionRes(@PathVariable ("menuId") int menuId) {
        // Get Menu options
        try{
            List <GetMenuOptionRes> getMenuOptionRes = storeProvider.getMenuOption(menuId);
            return new BaseResponse<>(getMenuOptionRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}