package com.bumsoap.petcare.schedule;

import com.bumsoap.petcare.service.appointment.IServiceAppointment;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AppoStatusChanger {
    private final IServiceAppointment appointService;


}
