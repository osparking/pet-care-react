package com.bumsoap.petcare.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppointmentUpdateRequest {
    private String date;
    private String time;
    private String reason;
}
