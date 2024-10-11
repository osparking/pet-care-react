package com.bumsoap.petcare.utils;

public class UrlMapping {
    public static final String API = "/api/v1";
    public static final String USERS = API + "/users";
    public static final String REGISTER_USER = "/register";
    public static final String UPDATE_USER = "/update/{userId}";
    public static final String USER_BY_ID = "/user/{userId}";
    public static final String DELETE_USER = "/delete/{userId}";
    public static final String GET_ALL = "/get_all";
    public static final String APPOINTMENT = API + "/appointments";
    public static final String CREATE = "/create";
    public static final String APPOINTMENT_BY_ID = "/{id}/get_id";
    public static final String DELETE_BY_ID = "/{id}/delete";
    public static final String APPOINTMENT_BY_NO = "/get_by_no/{no}";
    /**
     * 이것이 최신 유행 URL 형태
     */
    public static final String UPDATE_APPOINTMENT_BY_ID = "/{id}/update";
}
