package com.bumsoap.petcare.request;

import com.bumsoap.petcare.model.Appointment;
import com.bumsoap.petcare.model.Pet;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AppointmentRequest {
    private Appointment appointment;
    private List<Pet> pets;
}
