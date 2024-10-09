package com.bumsoap.petcare.service.appointment;

import com.bumsoap.petcare.model.Appointment;
import com.bumsoap.petcare.request.AppointmentRequest;

import java.util.List;

public interface IServiceAppointment {
    Appointment createAppointment(Appointment appointment,
                                  Long senderId, Long recipientId);
    List<Appointment> getAllAppointments();
    Appointment updateAppointment(Long id, AppointmentRequest appointment);

    void deleteAppointment(Long id);

    Appointment getAppointmentById(Long appointmentId);
    Appointment getByAppointmentNo(String appointmentNo);
}
