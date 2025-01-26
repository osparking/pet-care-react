package com.bumsoap.petcare.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Role {
    @Id
    private Long id;
    private String name;

}
