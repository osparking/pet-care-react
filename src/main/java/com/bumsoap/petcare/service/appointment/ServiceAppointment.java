package com.bumsoap.petcare.service.appointment;

import com.bumsoap.petcare.model.Appointment;
import com.bumsoap.petcare.repository.RepositoryAppointment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceAppointment implements  IServiceAppointment{

    private final RepositoryAppointment repositoryAppointment;
    
    @Override
    public Appointment createAppointment(Appointment appointment, Long senderId, Long recipientId) {
        return null;
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return List.of();
    }

    @Override
    public Appointment updateAppointment(Long id, Appointment appointment) {
        return null;
    }

    @Override
    public void deleteAppointment(Long id) {

    }

    @Override
    public Appointment getAppointmentById(Long appointmentId) {
        return null;
    }

    @Override
    public Appointment getByAppointmentNo(String appointmentNo) {
        return null;
    }
}
