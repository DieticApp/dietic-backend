package com.sardicus.dietic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    private Timestamp datetime = new Timestamp(System.currentTimeMillis());
    private String message;
    private String sent_by;
}
