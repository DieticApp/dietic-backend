package com.sardicus.dietic.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notes")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer note_id;
    private LocalDate date;
    private String note;
    private Boolean done = false;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "dietitian_id", nullable = false)
    private Dietitian dietitian;
}