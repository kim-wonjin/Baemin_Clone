package com.example.demo.src.store;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
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
public class StoreProvider {

    private final StoreDao storeDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public StoreProvider(StoreDao storeDao, JwtService jwtService) {
        this.storeDao = storeDao;
        this.jwtService = jwtService;
    }

    public List<GetStoreCategoriesRes> getStoreCategories() throws BaseException {
        try {
            List<GetStoreCategoriesRes> getStoreCategoriesRes = storeDao.getStoreCategories();
            return getStoreCategoriesRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetStoreRes getStore(int storeId) throws BaseException {
        try {
            GetStoreRes getStoreRes = storeDao.getStore(storeId);
            return getStoreRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetStoreByCategoryRes> getStoreByCategory(int categoryId) throws BaseException {
        try {
            List<GetStoreByCategoryRes> getStoreByCategoryRes = storeDao.getStoreByCategory(categoryId);
            return getStoreByCategoryRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetStoreMenuRes> getStoreMenu(int storeId) throws BaseException {
        try {
            List<GetStoreMenuRes> getStoreMenuRes = storeDao.getStoreMenu(storeId);
            return getStoreMenuRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetMenuOptionRes> getMenuOption(int menuId) throws BaseException {
        try {
            List<GetMenuOptionRes> getMenuOptionRes = storeDao.getMenuOption(menuId);
            return getMenuOptionRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
