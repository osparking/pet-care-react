package com.bumsoap.petcare.service.vet;

import com.bumsoap.petcare.dto.DtoUser;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public interface IServiceVet {
    List<String> getSpecialList();

    List<DtoUser> getAllVetsWithDetails();

    List<DtoUser> getAvailVetsForAppointment(String speciality,
                                             LocalDate date, LocalTime time);

    List<Map<String, Object>> countVetBySpecial();
}
