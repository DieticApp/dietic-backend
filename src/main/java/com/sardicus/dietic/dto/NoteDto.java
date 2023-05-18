package com.sardicus.dietic.dto;

import com.sardicus.dietic.entity.Dietitian;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteDto {

    private Integer note_id;
    private LocalDate date;
    private String note;
    private Boolean done = false;
    private Integer dietitian_id;
}
