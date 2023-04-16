package com.sardicus.dietic.dto;

import lombok.Data;

import java.util.Set;

@Data
public class PatientDto {

    private Integer patient_id;
    private String name;
    private String surname;
    private Set<DietPlanDto> dietPlans;

}
