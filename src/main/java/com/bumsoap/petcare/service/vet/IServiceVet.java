package com.bumsoap.petcare.service.vet;

import com.bumsoap.petcare.dto.DtoUser;

import java.util.List;

public interface IServiceVet {
    List<DtoUser> getAllVetsWithDetails();
}
