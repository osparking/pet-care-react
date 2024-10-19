package com.bumsoap.petcare.dto;

import com.bumsoap.petcare.utils.StatusAppointment;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class DtoAppointment {
    private Long id;
    private LocalDate date;
    private LocalTime time;
    private LocalDate createdDate;
    private String reason;
    private StatusAppointment status;
    private String appointmentNo;
    private DtoUser patient;
    private DtoUser veterinarian;
    private List<DtoPet> pets;
}
