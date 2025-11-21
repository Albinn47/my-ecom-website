package com.ecommerce.project.security.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

//Defines the format of how a request should come in to the backend server
@Data
public class LoginRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
