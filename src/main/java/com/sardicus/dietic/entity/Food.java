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
@Table(name = "food")
public class Food {
    @Id
    @Column(name = "food_id")
    @GeneratedValue
    private Integer food_id;
    @Column(name = "description")
    private String description;
    @Column(name = "Protein" , columnDefinition="Decimal(4,2))")
    private Double protein;
    @Column(name = "Fat" , columnDefinition="Decimal(5,2))")
    private Double fat;
    @Column(name = "Carbohydrate" , columnDefinition="Decimal(4,2))")
    private Double carb;
    @Column(name = "Energy")
    private Integer energy;

    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DietPlan> dietPlans = new HashSet<>();
}