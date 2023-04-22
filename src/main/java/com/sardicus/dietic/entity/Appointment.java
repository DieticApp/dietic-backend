package com.sardicus.dietic.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "appointments")
public class Appointment{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointment_id;
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());
    private LocalDate appointmentDate;
    private Time appointmentTime;
    private AppointmentStatus status = AppointmentStatus.BOOKED;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "dietitian_id", nullable = false)
    private Dietitian dietitian;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

}
