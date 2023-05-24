package com.sardicus.dietic.dto;

import com.google.cloud.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FirestoreDto {
    private String email;
    private String password;
    private String name;
    private String profile_pic;
    private Timestamp date_time;
}
