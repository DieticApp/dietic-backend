package com.sardicus.dietic.dto;

import com.sardicus.dietic.entity.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeightDto {
    private Integer id;
    private Integer patient_id;
    private Double weight;
    private LocalDate date;

}
