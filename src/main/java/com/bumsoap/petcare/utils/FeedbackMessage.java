package com.bumsoap.petcare.utils;

public class FeedbackMessage {
    public static final String CREATED = "자원 생성 성공!";
    public static final String FOUND = "자원 읽기 성공!";
    public static final String NOT_FOUND = "존재하지 않는 자원!";
    public static final String RESOURCE_UPDATED = "자원 갱신 성공!";
    public static final String RESOURCE_DELETED = "자원 삭제 성공!";
    public static final String PATIENT_OR_VETERINARIAN_NOT_FOUND
            = "동물 주인 혹은 수의사 부재!";
    public static final String ILLEGAL_APPOINTMENT_UPDATE = "예약의 불법적인 갱신";
    public static final String SERVER_ERROR = "팻 응용 서버 내부 오류";
    public static final String INVALID_VAT_ID = "수의사는 자신을 리뷰할 수 없음";
    public static final String ALREADY_REVIEWED = "당신은 이 수의사를 리뷰한 적이 있음";
    public static final String COMPLETED_APPOINTMENT_REQUIRED =
            "진료를 예약하고 완료한 환자만 리뷰할 수 있음";
    public static final String VET_OR_PAT_NOT_FOUND = "수의사 혹은 환자 부재";
    public static final String NO_VETS_FOUND = "수의사 부재";
}
