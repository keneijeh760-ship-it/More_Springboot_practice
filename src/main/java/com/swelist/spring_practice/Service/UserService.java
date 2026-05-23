package com.swelist.spring_practice.Service;

import com.swelist.spring_practice.Repository.UserRepository;
import com.swelist.spring_practice.dto.RegisterRequest;
import com.swelist.spring_practice.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class UserService {

    private final UserRepository userRepository;


    public void createUser(RegisterRequest registerRequest) {

         User user = mapToUser(registerRequest);
         Optional<User> optionalUser = userRepository.findUserByEmail(registerRequest.getEmail());
         if (optionalUser.isPresent()) {
             throw new RuntimeException("User with EMAIL " + user.getEmail() + " already exists");
         }

         userRepository.save(user);


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
