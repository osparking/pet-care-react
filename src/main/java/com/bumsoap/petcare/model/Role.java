package com.bumsoap.petcare.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import java.util.Collection;
import java.util.HashSet;

@Entity
@NoArgsConstructor
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NaturalId
    private String name;
    public Role(String name) {
        this.name = name;
    }

    @ManyToMany
    private Collection<User> users = new HashSet<>();
    public String getName() {
        return name == null ? "" : name;
    }
}
