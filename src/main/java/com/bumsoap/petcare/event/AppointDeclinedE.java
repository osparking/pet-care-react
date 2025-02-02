package com.bumsoap.petcare.event;

import com.bumsoap.petcare.model.Appointment;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class AppointDeclinedE extends ApplicationEvent {
    private Appointment appointment;
    public AppointDeclinedE(Appointment appointment) {
        super(appointment);
        this.appointment = appointment;
    }
}
