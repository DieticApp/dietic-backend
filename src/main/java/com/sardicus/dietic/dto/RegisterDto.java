package com.sardicus.dietic.dto;

import com.sardicus.dietic.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

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


}
