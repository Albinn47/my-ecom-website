package com.ecommerce.project.security.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserInfoResponse {
    private Long id;
    //private String jwtToken;
    private String username;
    private List<String> roles;
    private String email;
    private String image;

    public UserInfoResponse(Long id, String username, String email ,List<String> roles , String image) {
        this.id = id;
        this.username = username;
        this.roles = roles;
        this.email = email;
        this.image = image;
    }
}
