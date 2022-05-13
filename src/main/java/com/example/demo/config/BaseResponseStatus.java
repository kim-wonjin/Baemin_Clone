package com.example.demo.config;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),


    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,2003,"권한이 없는 유저의 접근입니다."),

    // users
    USERS_EMPTY_USER_ID(false, 2010, "유저 아이디 값을 확인해주세요."),
    EMPTY_TOKEN(false, 2011, "TOKEN을 확인해주세요."),
    INVALID_TOKEN(false, 2012, "유효하지 않은 토큰입니다."),
    KAKAO_LOGIN_FAIL(false, 2013, "카카오 로그인에 실패하였습니다."),

    // [POST] /users
    POST_USERS_EMPTY_USER_NAME(false, 2015, "닉네임을 입력해주세요."),
    POST_USERS_EMPTY_PHONE_NUM(false, 2016, "전화번호를 입력해주세요."),
    POST_USERS_EMPTY_PASSWORD(false, 2017, "비밀번호를 입력해주세요."),
    POST_USERS_EMPTY_EMAIL(false, 2018, "이메일을 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, 2019, "이메일 형식을 확인해주세요."),
    POST_USERS_EXISTS_EMAIL(false,2020,"중복된 이메일입니다."),
    POST_USERS_EMPTY_ADDRESS(false, 2021, "주소를 입력해주세요."),
    POST_USERS_EXISTS_COUPON(false,2022,"이미 발급된 쿠폰입니다."),

    // [POST] /order
    POST_ORDER_NO_CART(false,2023,"장바구니를 찾지 못했습니다."),


    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),

    // [POST] /users
    DUPLICATED_EMAIL(false, 3013, "중복된 이메일입니다."),
    FAILED_TO_LOGIN(false,3014,"없는 아이디거나 비밀번호가 틀렸습니다."),



    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),

    //[PATCH] /users/{userIdx}
    MODIFY_FAIL_USERINFO(false,4014,"유저정보 수정에 실패하였습니다"),
    DELETE_FAIL_ADDRESS(false,4015,"유저주소 삭제에 실패하였습니다"),
    MODIFY_FAIL_ADDRESS(false,4016,"유저주소 수정에 실패하였습니다"),

    //[POST] /order
    CREATE_FAIL_ORDER_MENU(false,4016,"주문 메뉴 생성에 실패하였습니다."),
    UPDATE_CART_PRICE_FAIL(false,4018,"장바구니 금액 변동에 실패하였습니다."),
    UPDATE_CART_STATUS_FAIL(false,4019,"장바구니 상태 변경에 실패하였습니다."),
    UPDATE_ORDER_COUNT_FAIL(false,4020,"매장 주문량 증가에 실패하였습니다."),
    //[DELETE] /order
    DELETE_FAIL_ORDER_MENU(false,4017,"주문 메뉴 삭제에 실패하였습니다"),

    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다.");



    // 5000 : 필요시 만들어서 쓰세요
    // 6000 : 필요시 만들어서 쓰세요


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
