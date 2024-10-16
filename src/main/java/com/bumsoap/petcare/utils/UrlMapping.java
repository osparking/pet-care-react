package com.bumsoap.petcare.utils;

public class UrlMapping {
    public static final String API = "/api/v1";
    public static final String USERS = API + "/users";
    public static final String REGISTER_USER = "/register";
    public static final String UPDATE_USER = "/update/{userId}";
    public static final String USER_BY_ID = "/user/{userId}";
    public static final String DELETE_USER = "/delete/{userId}";
    public static final String GET_ALL = "/get_all";

    /*========================== Start Common API =================================*/
    /**
     * 아래 URL 형태가 최신 유행
     */
    public static final String UPDATE_BY_ID = "/{id}/update";
    public static final String GET_BY_ID = "/{id}/get_id";
    public static final String DELETE_BY_ID = "/{id}/delete";
    public static final String CREATE = "/create";
    /*========================== End Common API =================================*/

    /*========================== Start Appointment API =================================*/
    public static final String APPOINTMENT = API + "/appointments";
    public static final String APPOINTMENT_BY_NO = "/{no}/get_no";
    public static final String PETS = API + "/pets";
    public static final String ADD_PETS_FOR_APPOINTMENT = "/for_appointment";
    /*========================= End Appointment API ================================*/

    /*============================ Start Photo API ===================================*/
    public static final String PHOTO = API + "/photos";
    public static final String UPLOAD = "/upload";
    /*============================ End Photo API ===================================*/

    /*============================ Start Review API ===============================*/
    public static final String REVIEWS = API + "/reviews";
    /*============================ End Review API ===============================*/

}
