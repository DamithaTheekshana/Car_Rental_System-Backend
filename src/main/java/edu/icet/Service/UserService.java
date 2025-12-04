package edu.icet.Service;

import edu.icet.Model.Dto.LoginRequestDto;
import edu.icet.Model.Dto.RegistrationRequestDto;
import edu.icet.Model.Dto.UserResponseDto;
import edu.icet.Model.Entity.Users;
import edu.icet.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserResponseDto register(RegistrationRequestDto req) throws IllegalAccessException {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new IllegalAccessException("Email already in use");
        }
        if (req.getNic() != null && userRepository.existsByNic(req.getNic())) {
            throw new IllegalAccessException("Nic already in use");
        }

        Users users =new Users();
        users.setName(req.getFullName());
        users.setEmail(req.getEmail());
        users.setPassword(passwordEncoder.encode(req.getPassword()));
        users.setPhone(req.getPhoneNumber());
        users.setNic(req.getNic());
        users.setRole(req.getRole() != null ? req.getRole() : Users.Role.CUSTOMER);

        Users saved = userRepository.save(users);
        return toResponse(saved);
    }

    public UserResponseDto login(LoginRequestDto req){
        Optional<Users>maybe=userRepository.findByEmail(req.getEmail());
        if (maybe.isEmpty()){
            throw new IllegalArgumentException("Invalid email or password");
        }

        Users users = maybe.get();
//        boolean val =req.getPassword().equals(users.getPassword());
        boolean val = passwordEncoder.matches(req.getPassword(), users.getPassword());

        if (!val){
            throw new IllegalArgumentException("Invalid email or password");
        }
        return toResponse(users);
    }

    private UserResponseDto toResponse(Users u) {
        return new UserResponseDto(u.getUserId(),
                u.getName(),
                u.getEmail(),
                u.getPhone(),
                u.getNic(),
                u.getRole());
    }
}
