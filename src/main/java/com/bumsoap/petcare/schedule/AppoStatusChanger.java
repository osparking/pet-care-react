package com.bumsoap.petcare.schedule;

import com.bumsoap.petcare.service.appointment.IServiceAppointment;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class AppoStatusChanger {
    private final IServiceAppointment appointService;

    public void automateAppointStatusChange() {
        List<Long> appointIds = appointService.appointIds();
        for (Long appointId : appointIds) {
            appointService.setAppointStatus(appointId);
        }
    }
}
