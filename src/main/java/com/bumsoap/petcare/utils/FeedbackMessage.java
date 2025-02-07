package com.bumsoap.petcare.utils;

public class FeedbackMessage {
    public static final String CREATED_APPOINT = "진료 예약 등록 성공.";
    public static final String CREATED_PHOTO = "사진 등록 성공.";
    public static final String CREATED_PET = "예약에 팻 추가 성공.";
    public static final String CREATED_PETS = "예약에 팻들 추가 성공.";
    public static final String CREATED_REVIEW = "진료 리뷰 등록 성공.";
    public static final String CREATED_USER = "사용자 등록 성공.";

    public static final String FOUND_USER_REVIEWS = "유저의 모든 리뷰 채취.";
    public static final String FOUND_ALL_VETS = "모든 수의사 채취.";
    public static final String FOUND_ALL_USERS = "모든 사용자 채취.";
    public static final String FOUND_USER_BY_ID = "아이디로 사용차 채취";
    public static final String FOUND_PET_BY_ID = "아이디로 애완동물 채취";
    public static final String FOUND_APPOINT_BY_NO = "번호로 예약건 채취";
    public static final String FOUND_PHOTO_BY_ID = "아이디로 사진 채취";
    public static final String FOUND_ALL_SPECIAL = "모든 수의사 전문분야 채취";
    public static final String FOUND_ALL_AVAIL_VETS = "모든 예약가능 수의사 채취";
    public static final String FOUND_VET_COUNTS_BY_SPECIAL = "전문분야별 수의사 수 채취";
    public static final String FOUND_APPOINT_DTO_BY_ID = "예약 ID로 예약 DTO 채취";
    public static final String FOUND_VET_AGV_RATING_BY_ID = "수의사 ID로 평균 평가 채취";
    public static final String FOUND_USER_COUNT_BY_TYPE = "유형별 유저 숫자 채취";
    public static final String FOUND_PET_TYPES = "팻 유형 채취";
    public static final String FOUND_PET_COLORS = "팻 색상 전부 채취";
    public static final String FOUND_PET_BREEDS = "팻 품종 전부 채취";
    public static final String FOUND_USER_COUNT_BY_MO_TYPE
            = "등록 월 및 유저 유형별 등록자 수 채취";
    public static final String FOUND_USER_COUNT_BY_ACT_TYPE
            = "활성 상태 및 유형별 유저 수 채취";
    public static final String FOUND_PATIENTS = "모든 팻 주인 목록 채취";
    public static final String FOUND_ALL_APPOINT = "모든 예약 목록 채취";

    public static final String NOT_FOUND_USER_ID = "존재하지 않는 유저 ID";
    public static final String NOT_FOUND_PHOTO_ID = "존재하지 않는 사진 ID";
    public static final String NOT_FOUND_APPOINT_ID = "존재하지 않는 예약 ID";
    public static final String NOT_FOUND_VET_ID = "존재하지 않는 수의사 ID";
    public static final String NOT_FOUND_VET_OR_PAT = "수의사 혹은 환자 부재";
    public static final String NOT_FOUND_VET_SPECIAL = "전문분야 수의사 부재";
    public static final String NOT_FOUND_PHOTO = "사진 비 등록 유저";
    public static final String NOT_FOUND_REVIEW_ID = "존재하지 않는 리뷰 ID";
    public static final String NOT_FOUND_PET_ID = "존재하지 않는 팻 ID";
    public static final String NOT_FOUND_USER_EMAIL = "존재하지 않는 유저 이메일";
    public static final String NOT_FOUND_ROLE = "존재하지 않는 롤 오류";

    public static final String UPDATED_PWD = "비밀번호 갱신 성공";
    public static final String UPDATED_PET = "팻 갱신 완료";
    public static final String UPDATED_USER = "유저 갱신 완료";
    public static final String UPDATED_REVIEW = "리뷰 갱신 완료";
    public static final String UPDATED_PHOTO = "사진 갱신 완료";
    public static final String UPDATED_APPOINT = "예약 갱신 완료";

    public static final String DELETED_PET = "팻 삭제 완료";
    public static final String DELETED_PHOTO = "사진 삭제 완료";
    public static final String DELETED_REVIEW = "리뷰 삭제 완료";
    public static final String DELETED_USER = "유저 삭제 완료";
    public static final String DELETED_APPOINT = "예약 삭제 완료";
    public static final String DELETED_TOKEN = "토큰 삭제 완료";

    public static final String INVALID_TOKEN = "잘못된 토큰";
    public static final String ILLEGAL_APPOINTMENT_UPDATE = "예약의 불법적인 갱신";
    public static final String SERVER_ERROR = "팻 응용 서버 내부 오류";
    public static final String INVALID_VAT_ID = "수의사는 자신을 리뷰할 수 없음";
    public static final String ALREADY_REVIEWED = "당신은 이 수의사를 리뷰한 적이 있음";
    public static final String COMPLETED_APPOINTMENT_REQUIRED =
            "진료를 예약하고 완료한 환자만 리뷰할 수 있음";
    public static final String APMT_CANNOT_BE_CANCEL = "예약 취소 불가";
    public static final String APMT_CANNOT_BE_APPROVED = "예약 승인 불가";
    public static final String APPOINTMENT_CANCELED = "예약 취소 완료";
    public static final String APPOINTMENT_APPROVED = "예약 승인 완료";
    public static final String APPOINTMENT_DECLINED = "예약 거부 처리";
    public static final String APPO_STAT_COLLECTED = "예약상태 건수통계수집 성공";
    public static final String AUTH_SUCCESS = "계정 인증 성공";
    public static final String DISABLED_USER = "사용 중지된 계정";
    public static final String BAD_CREDENTIAL = "로그인 자격 정보 오류";

    public static final String VERIFIED_TOKEN = "검증된 토큰";
    public static final String TOKEN_VALIDATED = "계정 활성화됨";
    public static final String TOKEN_VALI_ERROR = "토큰 검증 오류";
    public static final String TOKEN_EXPIRED = "토큰 기한 만료";
    public static final String TOKEN_IS_VALID = "토큰 아직 유용함";
    public static final String TOKEN_SAVED = "토큰 저장 성공";

    public static final String APPOINT_COMPLETED = "예약 완료 처리됨";
    public static final String USER_ENABLED_TOGGLED = "유저 활성화 토글됨";
    public static final String NOT_FOUND_VERIF_TOKEN = "검증 토큰 부재 오류";
}