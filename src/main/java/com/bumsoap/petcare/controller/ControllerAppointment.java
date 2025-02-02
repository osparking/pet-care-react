package com.bumsoap.petcare.controller;

import com.bumsoap.petcare.event.AppointApprovedE;
import com.bumsoap.petcare.event.AppointmentBooked;
import com.bumsoap.petcare.exception.ResourceNotFoundException;
import com.bumsoap.petcare.model.Appointment;
import com.bumsoap.petcare.request.AppointmentRequest;
import com.bumsoap.petcare.request.AppointmentUpdateRequest;
import com.bumsoap.petcare.response.ApiResponse;
import com.bumsoap.petcare.service.appointment.ServiceAppointment;
import com.bumsoap.petcare.utils.FeedbackMessage;
import com.bumsoap.petcare.utils.UrlMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(UrlMapping.APPOINTMENT)
@CrossOrigin("http://localhost:5173/")
@RequiredArgsConstructor
public class ControllerAppointment {
    private final ServiceAppointment serviceAppointment;
    private final ApplicationEventPublisher eventPublisher;

    @PutMapping(UrlMapping.UPDATE_BY_ID)
    public ResponseEntity<ApiResponse> updateAppointment(
            @PathVariable Long id,
            @RequestBody AppointmentUpdateRequest appointment) {
        try {
            return ResponseEntity.ok().body(
                    new ApiResponse(FeedbackMessage.RESOURCE_UPDATED,
                            serviceAppointment.updateAppointment(id, appointment)));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(NOT_ACCEPTABLE)
                    .body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping(UrlMapping.DELETE_BY_ID)
    public ResponseEntity<ApiResponse> deleteAppointment(@PathVariable Long id) {
        try {
            serviceAppointment.deleteAppointment(id);
            return ResponseEntity.ok().body(
                    new ApiResponse(FeedbackMessage.RESOURCE_DELETED, null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping(UrlMapping.APPOINTMENT_BY_NO)
    public ResponseEntity<ApiResponse> getByAppointmentNo(@PathVariable String no) {
        try {
            return ResponseEntity.status(FOUND)
                    .body(new ApiResponse(FeedbackMessage.FOUND,
                            serviceAppointment.getByAppointmentNo(no)));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping(UrlMapping.GET_BY_ID)
    public ResponseEntity<ApiResponse> getAppointmentById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(FOUND)
                    .body(new ApiResponse(FeedbackMessage.FOUND,
                            serviceAppointment.getDtoAppointmentById(id)));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    /**
     * 애완동물 수의사 진료 예약을 신청한다.
     *
     * @param appointment 겸진 예약 요청 정보
     * @param senderId    애완동물 쇼유자 ID
     * @param recipientId 수의사 ID
     * @return ResponseEntity<ApiResponse> 반응 상태 및 예약 요청 객체
     */
    @PostMapping(UrlMapping.CREATE)
    public ResponseEntity<ApiResponse> bookAppointment(
            @RequestBody AppointmentRequest appointment,
            @RequestParam Long senderId,
            @RequestParam Long recipientId) {
        try {
            Appointment savedAppointment = serviceAppointment.createAppointment(
                    appointment, senderId, recipientId);
            eventPublisher.publishEvent(new AppointmentBooked(savedAppointment));
            return ResponseEntity.status(CREATED)
                    .body(new ApiResponse(FeedbackMessage.CREATED, savedAppointment));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping(UrlMapping.GET_ALL)
    public ResponseEntity<ApiResponse> getAllAppointments() {
        try {
            return ResponseEntity.status(FOUND)
                    .body(new ApiResponse(FeedbackMessage.FOUND,
                            serviceAppointment.getAllAppointments()));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping(UrlMapping.COUNT_ALL)
    public long countAllAppointments(){
        return serviceAppointment.countAllAppointments();
    }

    @PutMapping(UrlMapping.APPO_COMPLETE)
    public ResponseEntity<ApiResponse> completeAppointment(
            @PathVariable Long id) {
        try {
            var appo = serviceAppointment.completeAppointment(id);
            return ResponseEntity.status(OK)
                    .body(new ApiResponse(FeedbackMessage.RESOURCE_UPDATED,
                            appo.getStatus()));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping(UrlMapping.APPO_CANCEL)
    public ResponseEntity<ApiResponse> cancelAppointment(@PathVariable Long id) {
        try {
            Appointment appointment = serviceAppointment.cancelAppointment(id);
            return ResponseEntity.ok(
                    new ApiResponse(FeedbackMessage.APPOINTMENT_CANCELED, appointment));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(NOT_ACCEPTABLE)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping(UrlMapping.APPO_APPROVE)
    public ResponseEntity<ApiResponse> approveAppointment(@PathVariable Long id) {
        try {
            Appointment appointment = serviceAppointment.approveAppointment(id);
            eventPublisher.publishEvent(new AppointApprovedE(appointment));
            return ResponseEntity.ok(
                    new ApiResponse(FeedbackMessage.APPOINTMENT_APPROVED, appointment));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(NOT_ACCEPTABLE)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping(UrlMapping.APPO_DECLINE)
    public ResponseEntity<ApiResponse> declineAppointment(@PathVariable Long id) {
        try {
            Appointment appointment = serviceAppointment.declineAppointment(id);
            return ResponseEntity.ok(
                    new ApiResponse(FeedbackMessage.APPOINTMENT_DECLINED, appointment));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping(UrlMapping.APPOINT_DATA)
    public ResponseEntity<ApiResponse> getAppointData(){
        try {
            List<Map<String, Object>> data = serviceAppointment.getAppointData();
            return ResponseEntity.ok(
                    new ApiResponse(FeedbackMessage.APPO_STAT_COLLECTED, data));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

}
