package edu.icet.Model.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data //getter/setter/toString(), equals(), hashCode()
public class LoginRequestDto {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

}
