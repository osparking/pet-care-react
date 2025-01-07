package com.bumsoap.petcare.utils;

public enum StatusAppointment {
    CANCELLED,
    ON_GOING,
    UP_COMING,
    APPROVED,
    REJECTED,
    APPROVE_WAIT,
    PENDING,
    COMPLETED,
    ;
    @Override
    public String toString() {
        switch (this) {
            case CANCELLED:
                return "취소됨";
            case ON_GOING:
                return "진료 중";
            case UP_COMING:
                return "임박함";
            case APPROVED:
                return "승인됨";
            case REJECTED:
                return "거부됨";
            case APPROVE_WAIT:
                return "승인대기";
            case PENDING:
                return "보류 중";
            case COMPLETED:
                return "완료됨";
            default:
                return null;
        }
    }
}

