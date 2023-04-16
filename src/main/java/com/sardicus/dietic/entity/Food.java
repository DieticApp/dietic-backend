package com.sardicus.dietic.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "foods")
public class Food {
    @Id
    @Column(name = "food_id")
    @GeneratedValue
    private Integer id;
    @Column(name = "description")
    private String description;
    @Column(name = "Protein" , columnDefinition="Decimal(4,2))")
    private Integer protein;
    @Column(name = "Fat" , columnDefinition="Decimal(5,2))")
    private Integer fat;
    @Column(name = "Carbohydrate" , columnDefinition="Decimal(4,2))")
    private Integer carb;
    @Column(name = "Energy")
    private Integer energy;
}