package com.bumsoap.petcare.dto;

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
    private String status;
    private String appointmentNo;
    private DtoUserAppo patient;
    private DtoUserAppoVet veterinarian;
    private List<DtoPet> pets;
}
