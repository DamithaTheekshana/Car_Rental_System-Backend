package edu.icet.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDTO {

    private Long userId;
    private String name;
    private String email;
    private String password;
    private String nic;
    private String phone;
    private String role;
}
