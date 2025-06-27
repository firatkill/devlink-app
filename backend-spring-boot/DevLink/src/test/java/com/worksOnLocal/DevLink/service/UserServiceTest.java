package com.worksOnLocal.DevLink.service;

import com.worksOnLocal.DevLink.dto.request.UpdateUserRequestDTO;
import com.worksOnLocal.DevLink.dto.response.UpdateUserResponseDTO;
import com.worksOnLocal.DevLink.entity.User;
import com.worksOnLocal.DevLink.repository.UserRepository;
import com.worksOnLocal.DevLink.security.jwt.JWTService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock

    private  UserRepository userRepository;

    @Mock
    private  PasswordEncoder passwordEncoder;

    @Mock
    private  JWTService jwtService;

    @InjectMocks
    private UserService userService;


    private User user;
    private UpdateUserRequestDTO updateUserRequestDTO;

    @BeforeEach
    void setUp() {
        user=new User();
        user.setUsername("firatkil");
        user.setPassword("password");
        user.setEmail("firatkil@gmail.com");

        updateUserRequestDTO=new UpdateUserRequestDTO("firatkil","firatkil@gmail.com","password");

    }

    @Test
    void deleteUser_success(){
        userService.deleteUser(user);
        Mockito.verify(userRepository).delete(user);
    }

    @Test
    void updateUser_success(){
        Mockito.when(userRepository.findByUsername(updateUserRequestDTO.username())).thenReturn(Optional.of(user));
        Mockito.when(passwordEncoder.encode(updateUserRequestDTO.password())).thenReturn("hashedPassword");

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        Mockito.when(jwtService.generateToken(user.getUsername())).thenReturn("token");

        UpdateUserResponseDTO updateUserResponseDTO=userService.updateUser(updateUserRequestDTO,user);

        Assertions.assertEquals(user.getUsername(),updateUserResponseDTO.username());
        Assertions.assertEquals(user.getEmail(),updateUserResponseDTO.email());
        Assertions.assertEquals("token", updateUserResponseDTO.token());
    }
    @Test
    void updateUser_ThrowsExceptionWhenUserNotFoundWithUsername(){

        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,()->userService.updateUser(updateUserRequestDTO,user));
    }


}