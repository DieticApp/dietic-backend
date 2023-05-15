package com.sardicus.dietic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StepDto {
    private Integer id;
    private Integer patient_id;
    private Integer steps;
    private LocalDate date;

}
