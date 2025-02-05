package com.bumsoap.petcare.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SystemUtils {
    private static final int EXPIRE_MIN = 10;

    public static Date getExpireTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, EXPIRE_MIN);
        return new Date(calendar.getTime().getTime());
    }

    public static boolean isValidateFeedback(String token) {
        List<String> tokenValidationFeedback = new ArrayList<>();

        tokenValidationFeedback.add(FeedbackMessage.NOT_FOUND_VERIF_TOKEN);
        tokenValidationFeedback.add(FeedbackMessage.VERIFIED_TOKEN);
        tokenValidationFeedback.add(FeedbackMessage.TOKEN_EXPIRED);
        tokenValidationFeedback.add(FeedbackMessage.TOKEN_VALIDATED);

        if (!tokenValidationFeedback.contains(token)) {
            return true;
        } else {
            return false;
        }
    }
}
