package com.sardicus.dietic.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dietitians")
public class Dietitian {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dietitian_id;
    private String name;
    private String surname;
    private String email;
    @OneToMany(mappedBy = "dietitian", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Patient> patients = new HashSet<>();
    @OneToMany(mappedBy = "dietitian", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DietPlan> dietPlans = new HashSet<>();
}
