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
import java.util.*;
import java.util.stream.Collectors;

import static com.bumsoap.petcare.utils.StatusAppointment.*;

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
                    FeedbackMessage.NOT_FOUND_VET_OR_PAT);
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
                new ResourceNotFoundException(FeedbackMessage.NOT_FOUND_APPOINT_ID));
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
                            FeedbackMessage.NOT_FOUND_APPOINT_ID);
        });
    }

    @Override
    public DtoAppointment getDtoAppointmentById(Long appointmentId) {
        return repositoryAppointment.findById(appointmentId)
                .map(appo -> {
                    DtoAppointment appoDto = appoConverter
                            .mapEntityToDto(appo, DtoAppointment.class);
                    var dtoPets = appo.getPets().stream().map(
                            pet -> petConverter.mapEntityToDto(pet, DtoPet.class)).toList();
                    appoDto.setPets(dtoPets);
                    return appoDto;
                })
                .orElseThrow(() -> new ResourceNotFoundException(
                        FeedbackMessage.NOT_FOUND_APPOINT_ID));
    }

    @Override
    public Appointment getAppointmentById(Long appointmentId) {
        return repositoryAppointment
                .findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        FeedbackMessage.NOT_FOUND_APPOINT_ID));
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
                new ResourceNotFoundException(FeedbackMessage.NOT_FOUND_APPOINT_ID));
        if (apmt.getStatus().equals(APPROVE_WAIT)) {
            apmt.setStatus(StatusAppointment.CANCELLED);
            return repositoryAppointment.save(apmt);
        } else {
            throw new IllegalStateException(FeedbackMessage.APMT_CANNOT_BE_CANCEL);
        }
    }

    @Override
    public Appointment approveAppointment(Long apmtId) {
        return repositoryAppointment.findById(apmtId)
                .filter(apmt -> apmt.getStatus().equals(APPROVE_WAIT))
                .map(apmt -> {
                    apmt.setStatus(APPROVED);
                    return repositoryAppointment.saveAndFlush(apmt);})
                .orElseThrow(() -> new IllegalStateException(
                    FeedbackMessage.APMT_CANNOT_BE_APPROVED));
    }

    @Override
    public Appointment declineAppointment(Long apmtId) {
        return repositoryAppointment.findById(apmtId)
                .filter(apmt -> apmt.getStatus().equals(APPROVE_WAIT))
                .map(apmt -> {
                    apmt.setStatus(DECLINED);
                    return repositoryAppointment.saveAndFlush(apmt);})
                .orElseThrow(() -> new IllegalStateException(
                    FeedbackMessage.NOT_FOUND_APPOINT_ID));
    }

    @Override
    public long countAllAppointments() {
        return repositoryAppointment.count();
    }

    /**
     * 예약 상태(승인 대기 등)별 건수(count) 맵 반환 메소드
     * @param status 예약 상태
     * @param count 예약 건수
     * @return 상태-키, 건수-값 쌍의 맴 항목을 가지는 맴
     */
    private Map<String, Object> createStatusCountMap(
            StatusAppointment status, Long count) {
        Map<String, Object> countMap = new HashMap<>();
        countMap.put("name", status.toString());
        countMap.put("value", count);
        return countMap;
    }

    /**
     * 모든 예약을 상태별로 수를 헤아리고, 하나 이상인 상태를 모아 <상태명, 건수>의
     * 맵 목록을 게산한다.
     * @return 상태가 키이고, 건수가 값인 맵이 요소인 목록
     */
    @Override
    public List<Map<String, Object>> getAppointData() {
        var midPoint = getAllAppointments()
                .stream()
                .collect(Collectors.groupingBy(
                        Appointment::getStatus, Collectors.counting()))
                .entrySet();
        var result = midPoint
                .stream()
                .filter(entry -> entry.getValue() > 0)
                .map(entry -> createStatusCountMap(
                        entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        return result;
    }
}












