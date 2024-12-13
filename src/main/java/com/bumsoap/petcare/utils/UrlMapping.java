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
    public static final String ADD_PETS_FOR_APPOINTMENT = "/for_appointment";
    public static final String APPO_COMPLETE = "/{id}/complete";
    /*========================= End Appointment API ================================*/

    /*============================ Start Photo API ===================================*/
    public static final String PHOTO = API + "/photos";
    public static final String UPLOAD = "/upload";
    /*============================ End Photo API ===================================*/

    /*============================ Start Review API ===============================*/
    public static final String REVIEWS = API + "/reviews";
    public static final String GET_USER_REVIEWS = "/{userId}/get_user_id";
    public static final String GET_VET_STAR_AVG = "/{vetId}/vet_star_avg";
    /*============================ End Review API ===============================*/

    /*============================ Start VET API ===============================*/
    public static final String VETS = API + "/vets";
    public static final String GET_ALL_VETS = "/get_all_vets";
    public static final String GET_AVAILABLE_VETS = "/search_vets" ;
    /*============================ End VET API ===============================*/

    /*============================ Start Pet Type API ===============================*/
    public static final String PETS = API + "/pets";
    public static final String GET_PET_TYPES = "/get_types";
    public static final String GET_PET_COLORS = "/get_colors";
    public static final String GET_PET_BREEDS = "/get_breeds";
    /*============================ End Pet Type API ===============================*/

}
