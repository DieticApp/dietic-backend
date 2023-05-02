package com.sardicus.dietic.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JWTAuthResponse {
    private Integer id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer dietitianId;
    private String roleName;
    private String email;
    private String  name;
    private String surname;
    private String accessToken;
    private String tokenType = "Bearer";
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String firebaseResponse;



}
