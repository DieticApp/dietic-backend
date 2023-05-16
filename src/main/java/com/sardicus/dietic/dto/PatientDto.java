package com.sardicus.dietic.dto;

import lombok.Data;

@Data
public class PatientDto {

    private Integer patient_id;
    private Integer dietitian_id;
    private String email;
    private String name;
    private String surname;
    private Integer age;
    private Integer height;
    private Double weight;
    private Double bodyFat;
    private String about;
    private String picture;

}
