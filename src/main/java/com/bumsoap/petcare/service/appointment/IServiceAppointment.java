package com.bumsoap.petcare.service.appointment;

import com.bumsoap.petcare.dto.DtoAppointment;
import com.bumsoap.petcare.model.Appointment;
import com.bumsoap.petcare.request.AppointmentRequest;
import com.bumsoap.petcare.request.AppointmentUpdateRequest;

import java.util.List;
import java.util.Map;

public interface IServiceAppointment {
    Appointment createAppointment(
            AppointmentRequest request, Long senderId, Long recipientId);

    List<Appointment> getAllAppointments();
    Appointment updateAppointment(Long id, AppointmentUpdateRequest appointment);
    void deleteAppointment(Long id);

    DtoAppointment getDtoAppointmentById(Long appointmentId);

    Appointment getAppointmentById(Long appointmentId);
    Appointment getByAppointmentNo(String appointmentNo);

    Appointment completeAppointment(Long id);

    List<DtoAppointment> getAllDtoAppointmentsByUserId(Long userId);

    Appointment cancelAppointment(Long apmtId);

    Appointment approveAppointment(Long apmtId);

    Appointment declineAppointment(Long apmtId);

    long countAllAppointments();

    List<Map<String, Object>> getAppointData();
}
