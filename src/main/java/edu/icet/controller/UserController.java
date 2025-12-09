package edu.icet.controller;

import edu.icet.Model.Dto.AdminRegistationRequestDto;
import edu.icet.Model.Dto.LoginRequestDto;
import edu.icet.Model.Dto.RegistrationRequestDto;
import edu.icet.Model.Dto.UserResponseDto;
import edu.icet.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/userregister")
    public UserResponseDto register(@Valid @RequestBody RegistrationRequestDto req){
        try {
            return  userService.register(req);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    @PostMapping("/adminregister")
    public UserResponseDto registerAdmin(@Valid @RequestBody AdminRegistationRequestDto reqAdmin){
        try {
            return userService.registerAdmin(reqAdmin);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/login")
    public UserResponseDto login(@Valid @RequestBody LoginRequestDto req){

        return userService.login(req);
    }

}
