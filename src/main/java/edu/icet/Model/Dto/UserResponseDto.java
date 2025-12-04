package edu.icet.Model.Dto;

import edu.icet.Model.Entity.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

    private Long userId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String nic;
    private Users.Role role;

//    public UserResponseDto(){}
//
//    public UserResponseDto(Long userId, String fullName, String email, String phoneNumber, String nic, Users.Role role) {
//
//        this.userId = userId;
//        this.fullName = fullName;
//        this.email = email;
//        this.phoneNumber = phoneNumber;
//        this.nic = nic;
//        this.role = role;
//
//    }
}
