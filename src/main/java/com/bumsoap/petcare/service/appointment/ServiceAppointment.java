package com.bumsoap.petcare.service.appointment;

import com.bumsoap.petcare.dto.DtoAppointment;
import com.bumsoap.petcare.dto.DtoPet;
import com.bumsoap.petcare.dto.EntityConverter;
import com.bumsoap.petcare.exception.ResourceNotFoundException;
import com.bumsoap.petcare.model.Appointment;
import com.bumsoap.petcare.model.Pet;
import com.bumsoap.petcare.model.User;
import com.bumsoap.petcare.repository.RepositoryAppointment;
import com.bumsoap.petcare.repository.RepositoryUser;
import com.bumsoap.petcare.request.AppointmentRequest;
import com.bumsoap.petcare.request.AppointmentUpdateRequest;
import com.bumsoap.petcare.service.pet.ServicePet;
import com.bumsoap.petcare.utils.FeedbackMessage;
import com.bumsoap.petcare.utils.StatusAppointment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.bumsoap.petcare.utils.StatusAppointment.APPROVE_WAIT;

@Service
@RequiredArgsConstructor
public class ServiceAppointment implements IServiceAppointment {

    private final RepositoryAppointment repositoryAppointment;
    private final RepositoryUser repositoryUser;
    private final ServicePet servicePet;
    private final EntityConverter<Appointment, DtoAppointment> appoConverter;
    private final EntityConverter<Pet, DtoPet> petConverter;

    /** 두 ID 로 두 관련자를 읽고, 이를 예약 객체에 지정하고,
     * 두 속성 예약번호, 예약 상태도 지정한다. 관련자 중 한 명이라도 없으면, 예외를 던진다.
     * @param request - 예약 요청 객체
     *                    senderId - 환자/pet 주인 ID
     *                    recipientId - 수의사/vet' ID
     * return - 저장된 예약 객체
     * throws - ResourceNotFoundException
     *
     **/

    @Override
    @Transactional
    public Appointment createAppointment(
            AppointmentRequest request, Long senderId, Long recipientId) {

        Optional<User> patient = repositoryUser.findById(senderId);
        Optional<User> veterinarian = repositoryUser.findById(recipientId);

        if (patient.isPresent() && veterinarian.isPresent()) {
            Appointment appointment = request.getAppointment();
            List<Pet> pets = request.getPets();
            pets.forEach(pet -> pet.setAppointment(appointment));
            List<Pet> savedPets = servicePet.addPetsForAppointment(pets);
            appointment.setPets(savedPets);

            appointment.addPatient(patient.get());
            appointment.addVeterinarian (veterinarian.get());
            appointment.setAppointmentNo();
            appointment.setStatus(APPROVE_WAIT);
            return repositoryAppointment.save(appointment);
        } else {
            throw new ResourceNotFoundException(
                    FeedbackMessage.PATIENT_OR_VETERINARIAN_NOT_FOUND);
        }
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return repositoryAppointment.findAll();
    }

    @Override
    public Appointment updateAppointment(Long id, AppointmentUpdateRequest request) {
        Appointment oldAppointment = getAppointmentById(id);
        if (!Objects.equals(oldAppointment.getStatus(), APPROVE_WAIT)) {
            throw new IllegalStateException(
                    FeedbackMessage.ILLEGAL_APPOINTMENT_UPDATE);
        }
        oldAppointment.setDate(LocalDate.parse(request.getDate()));
        oldAppointment.setTime(LocalTime.parse(request.getTime()));
        oldAppointment.setReason(request.getReason());

        return repositoryAppointment.save(oldAppointment);
    }

    @Override
    public Appointment completeAppointment(Long id) {
        Appointment appo = repositoryAppointment.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(FeedbackMessage.NOT_FOUND));
        appo.setStatus(StatusAppointment.COMPLETED);
        return repositoryAppointment.save(appo);
    }

    @Override
    public void deleteAppointment(Long id) {
        repositoryAppointment
                .findById(id)
                .ifPresentOrElse(repositoryAppointment::delete,
                        () -> {
                    throw new ResourceNotFoundException(
                            FeedbackMessage.NOT_FOUND);
        });
    }

    @Override
    public Appointment getAppointmentById(Long appointmentId) {
        return repositoryAppointment
                .findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        FeedbackMessage.NOT_FOUND));
    }

    @Override
    public Appointment getByAppointmentNo(String appointmentNo) {
        return repositoryAppointment.findByAppointmentNo(appointmentNo);
    }

    @Override
    public List<DtoAppointment> getAllDtoAppointmentsByUserId(Long userId) {
        List<Appointment> appointments = repositoryAppointment
                .findAllByUserId(userId);
        return appointments.stream()
                .map(appo -> {
                    DtoAppointment appoDto = appoConverter
                            .mapEntityToDto(appo, DtoAppointment.class);
                    var dtoPets = appo.getPets().stream().map(
                            pet -> petConverter.mapEntityToDto(pet, DtoPet.class))
                            .toList();
                    appoDto.setPets(dtoPets);

                    return appoDto;
                }).toList();
    }

    @Override
    public Appointment cancelAppointment(Long apmtId) {
        Appointment apmt = repositoryAppointment.findById(apmtId).orElseThrow(() ->
                new ResourceNotFoundException(FeedbackMessage.NOT_FOUND));
        if (apmt.getStatus().equals(APPROVE_WAIT)) {
            apmt.setStatus(StatusAppointment.CANCELLED);
            return repositoryAppointment.save(apmt);
        } else {
            throw new IllegalStateException(FeedbackMessage.APMT_CANNOT_BE_CANCEL);
        }
    }
}












