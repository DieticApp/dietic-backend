package com.sardicus.dietic.dto;

import com.sardicus.dietic.entity.AppointmentStatus;
import lombok.Data;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Set;

@Data
public class AppointmentDto {
    private Long appointment_id;
    private AppointmentStatus status = AppointmentStatus.BOOKED;
    private Integer dietitian_id;
    private String dietitianName;
    private String dietitianSurname;
    private Integer patient_id;
    private String patientName;
    private String patientSurname;
    private LocalDate appointmentDate;
    private Time appointmentTime;
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());


}
