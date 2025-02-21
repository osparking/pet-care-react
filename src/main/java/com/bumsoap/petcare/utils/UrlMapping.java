package com.bumsoap.petcare.utils;

public class UrlMapping {
    public static final String API = "/api/v1";

    /*========================== Start Common API =================================*/
    /**
     * 아래 URL 형태가 최신 유행
     */
    public static final String UPDATE_BY_ID = "/{id}/update";
    public static final String GET_BY_ID = "/{id}/get_id";
    public static final String DELETE_BY_ID = "/{id}/delete";
    public static final String CREATE = "/create";
    public static final String COUNT_ALL = "/count";
    /*========================== End Common API =================================*/

    /*========================== Start User API =================================*/
    public static final String USERS = API + "/users";
    public static final String REGISTER_USER = "/register";
    public static final String UPDATE_USER = "/update/{userId}";
    public static final String USER_BY_ID = "/user/{userId}";
    public static final String DELETE_USER = "/delete/{userId}";
    public static final String GET_ALL = "/get_all";
    public static final String GET_ALL_PATIENTS = "/get_all_patients";
    public static final String CHANGE_PASSWORD = "/{userId}/change_pwd";
    public static final String COUNT_BY_TYPE = "/{type}/count";
    public static final String COUNT_BY_MONTH_USER_TYPE = "/count_month_utype";
    public static final String COUNT_BY_ACTIVE_STAT = "/active_stat";
    public static final String TOGGLE_ENABLED = "/toggle/{userId}/{flag}";
    /*========================== End User API =================================*/

    /*========================== Start Appointment API =================================*/
    public static final String APPOINTMENT = API + "/appointments";
    public static final String APPOINTMENT_BY_NO = "/{no}/get_no";
    public static final String ADD_PETS_FOR_APPOINTMENT = "/for_appointment";
    public static final String APPO_COMPLETE = "/{id}/complete";
    public static final String APPO_CANCEL = "/{id}/cancel";
    public static final String APPO_APPROVE = "/{id}/approve";
    public static final String APPO_DECLINE = "/{id}/decline";
    public static final String APPOINT_DATA = "/appoint_data";
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
    public static final String GET_ALL_SPECIALIZATIONS = "/get_all_specializations";
    public static final String GET_VET_COUNT_BY_SPECIAL = "/get_vet_count_by_special";
    /*============================ End VET API ===============================*/

    /*============================ Start Pet Type API ===============================*/
    public static final String PETS = API + "/pets";
    public static final String GET_PET_TYPES = "/get_types";
    public static final String GET_PET_COLORS = "/get_colors";
    public static final String GET_PET_BREEDS = "/get_breeds";
    public static final String ADD_PET_FOR_APPOINTMENT = "/{appointmentId}/insert";
    /*============================ End Pet Type API ===============================*/

    /*============================  Start Auth Type API ============================*/
    public static final String AUTH = API + "/auth";
    public static final String VERIFY_EMAIL = "/verify_email";
    public static final String LOGIN = "/login";
    public static final String RESEND_EMAIL = "/resend_email";
    public static final String REQUEST_PASSWORD_RESET = "/req_password_reset";
    public static final String RESET_PASSWORD = "/reset_password";
    /*========================== End AUth type API ============================= */

    /*============================  Start Verification Type API ====================*/
    public static final String VERIFY_TOKEN = API + "/verify" ;
    public static final String VALIDATE_TOKEN = "/validate_token" ;
    public static final String TOKEN_EXPIRED = "/is_token_expired";
    public static final String SAVE_TOKEN = "/save_token";
    public static final String GENERATE_NEW_TOKEN = "/generate_new_token";
    public static final String DELETE_TOKEN = "/delete_token";
    /*========================== End Verification type API ======================= */

    /*============================  Start Role API ============================*/
    public static final String ROLES = API + "/roles";
    public static final String GET_ALL_ROLES = "/get_all";
    public static final String GET_ROLE_BY_ID = "/get_by_id";
    public static final String GET_ROLE_BY_NAME = "/get_by_name";
    /*============================== End ROLE API =============================*/

}
