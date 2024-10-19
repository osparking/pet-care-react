package com.bumsoap.petcare.dto;

import lombok.Data;

@Data
public class DtoPet {
    private Long id;
    private String name;
    private String type;
    private String color;
    private String breed;
    private int age;
}
