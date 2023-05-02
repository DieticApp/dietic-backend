package com.sardicus.dietic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {


    private String last_message;
    private Timestamp last_message_time = new Timestamp(System.currentTimeMillis());
   private ArrayList<String> users;
}
