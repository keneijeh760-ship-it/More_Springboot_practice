package com.swelist.spring_practice.Service;

import com.swelist.spring_practice.Repository.UserRepository;
import com.swelist.spring_practice.dto.LoginRequest;
import com.swelist.spring_practice.dto.RegisterRequest;
import com.swelist.spring_practice.entity.User;
import com.swelist.spring_practice.security.JwtService;
import lombok.RequiredArgsConstructor;
import com.swelist.spring_practice.dto.AuthResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    public AuthResponse createUser(RegisterRequest registerRequest) {

         User user = mapToUser(registerRequest);
         Optional<User> optionalUser = userRepository.findUserByEmail(registerRequest.getEmail());
         if (optionalUser.isPresent()) {
             throw new RuntimeException("User with EMAIL " + user.getEmail() + " already exists");
         }

         userRepository.save(user);
        String token = jwtService.generateToken(user.getEmail());

        return AuthResponse.builder()
                .token(token)
                .build();


    }

    public AuthResponse login(LoginRequest loginRequest) {
        User user = userRepository.findUserByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        boolean passwordMatches = passwordEncoder.matches(
                loginRequest.getPassword(),
                user.getPassword()
        );

        if (!passwordMatches) {
            throw new RuntimeException("Invalid email or password");
        }
        String token = jwtService.generateToken(user.getEmail());

        return AuthResponse.builder()
                .token(token)
                .build();
    }

    private User mapToUser(RegisterRequest registerRequest) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();


    }
}
