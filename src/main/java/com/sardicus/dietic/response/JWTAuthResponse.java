package com.sardicus.dietic.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JWTAuthResponse {
    private String roleName;
    private String  name;
    private String surname;
    private String accessToken;
    private String tokenType = "Bearer";


}
