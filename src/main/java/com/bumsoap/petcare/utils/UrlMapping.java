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
    public static final String GET_BY_ID = "/{id}/get_id";
    /*========================== End Common API =================================*/

    /*========================== Start Appointment API =================================*/
    public static final String APPOINTMENT = API + "/appointments";
    /**
     * 아래 URL 형태가 최신 유행
     */
    public static final String UPDATE_APPOINTMENT_BY_ID = "/{id}/update";
    public static final String CREATE = "/create";
    public static final String DELETE_BY_ID = "/{id}/delete";
    public static final String APPOINTMENT_BY_NO = "/{no}/get_no";
    public static final String PETS = API + "/pets";
    public static final String ADD_PETS_FOR_APPOINTMENT = "/for_appointment";
    /*========================= End Appointment API ================================*/

}
