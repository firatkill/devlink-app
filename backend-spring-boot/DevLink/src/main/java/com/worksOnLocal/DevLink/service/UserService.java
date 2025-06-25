package com.worksOnLocal.DevLink.service;

import com.worksOnLocal.DevLink.dto.request.UpdateUserRequestDTO;
import com.worksOnLocal.DevLink.dto.response.UpdateUserResponseDTO;
import com.worksOnLocal.DevLink.entity.User;
import com.worksOnLocal.DevLink.repository.UserRepository;
import com.worksOnLocal.DevLink.security.jwt.JWTService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public UpdateUserResponseDTO updateUser(UpdateUserRequestDTO updateUserRequestDTO, User user) {
        User userToUpdate=userRepository.findByUsername(user.getUsername()).orElseThrow(()-> new EntityNotFoundException("Kullanıcı Bulunamadı"));

        if(updateUserRequestDTO.username()!=null) userToUpdate.setUsername(updateUserRequestDTO.username());
        if(updateUserRequestDTO.email()!=null) userToUpdate.setEmail(updateUserRequestDTO.email());
        if(updateUserRequestDTO.password()!=null){
            String hashedPassword= passwordEncoder.encode(updateUserRequestDTO.password());
            userToUpdate.setPassword(hashedPassword);
        }

        User savedUser=userRepository.save(userToUpdate);

        String token= jwtService.generateToken(savedUser.getUsername());
        return new UpdateUserResponseDTO(savedUser.getUsername(), savedUser.getEmail(),token);


    }
}
