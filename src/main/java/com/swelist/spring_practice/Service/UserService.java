package com.swelist.spring_practice.Service;

import com.swelist.spring_practice.Repository.UserRepository;
import com.swelist.spring_practice.dto.RegisterRequest;
import com.swelist.spring_practice.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser(RegisterRequest registerRequest) {

         User user = mapToUser(registerRequest);
         Optional<User> optionalUser = userRepository.findById(user.getId());

         if (optionalUser.isPresent()) {
             throw new RuntimeException("User with id " + user.getId() + " already exists");
         }
         BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
         user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
         return userRepository.save(user);


    }

    private User mapToUser(RegisterRequest registerRequest) {
        return User.builder()
                .id(registerRequest.getId())
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password(registerRequest.getPassword())
                .build();


    }
}
