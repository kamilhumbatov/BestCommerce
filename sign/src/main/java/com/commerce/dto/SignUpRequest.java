package com.commerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {

    @NotBlank(message = "Name must not be empty!")
    @Size(min = 4, max = 40, message = "Name size must be between 4 and 40")
    private String name;

    @NotBlank(message = "Username must not be empty!")
    @Size(min = 3, max = 15, message = "Username size must be between 3 and 15")
    private String username;

    @NotBlank(message = "Email must not be empty!")
    @Size(max = 40)
    @Email(message = "Email format invalid!")
    private String email;

    @NotBlank(message = "Password must not be empty!")
    @Size(min = 6, max = 20, message = "Password size must be between 6 and 20")
    private String password;
}
