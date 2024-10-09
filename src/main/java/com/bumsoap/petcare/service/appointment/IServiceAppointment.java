package com.bumsoap.petcare.service.appointment;

import com.bumsoap.petcare.model.Appointment;
import com.bumsoap.petcare.request.AppointmentUpdateRequest;

import java.util.List;

public interface IServiceAppointment {
    Appointment createAppointment(Appointment appointment,
                                  Long senderId, Long recipientId);
    List<Appointment> getAllAppointments();
    Appointment updateAppointment(Long id, AppointmentUpdateRequest appointment);

    void deleteAppointment(Long id);

    Appointment getAppointmentById(Long appointmentId);
    Appointment getByAppointmentNo(String appointmentNo);
}
