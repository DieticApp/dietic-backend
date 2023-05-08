package com.sardicus.dietic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {
    private String name;
    private String surname;
    private String email;
    private String password;
    private String roleName;
    private Integer dietitianId;
    private Integer age;
    private Integer height;
    private Double weight;
    private Double bodyFat;
    private String about;





}
