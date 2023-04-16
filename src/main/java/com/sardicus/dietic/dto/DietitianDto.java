package com.sardicus.dietic.dto;

import lombok.Data;

import java.util.Set;
@Data
public class DietitianDto {
    private Integer dietitian_id;
    private String name;
    private String surname;
    private String email;
   private Set<PatientDto> patients;
    private Set<DietPlanDto> dietPlans;
}
