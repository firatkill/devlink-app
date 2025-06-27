package com.worksOnLocal.DevLink.service;

import com.worksOnLocal.DevLink.dto.request.CreateUserRequestDTO;
import com.worksOnLocal.DevLink.dto.request.LoginRequestDTO;
import com.worksOnLocal.DevLink.dto.response.LoginResponseDTO;
import com.worksOnLocal.DevLink.entity.User;
import com.worksOnLocal.DevLink.exception.EmailAlreadyExistsException;
import com.worksOnLocal.DevLink.exception.UsernameAlreadyExistsException;
import com.worksOnLocal.DevLink.exception.WrongCredentialsException;
import com.worksOnLocal.DevLink.repository.UserRepository;
import com.worksOnLocal.DevLink.security.jwt.JWTService;
import io.jsonwebtoken.lang.Assert;
import jakarta.persistence.EntityNotFoundException;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private  UserRepository userRepository;

    @Mock
    private  PasswordEncoder passwordEncoder;

    @Mock
    private  JWTService jwtService;

    @Mock
    private  ProfileService profileService;

    @InjectMocks
    private AuthService authService;


    private CreateUserRequestDTO createUserRequestDTO;
    private LoginRequestDTO loginRequestDTO;
    private User user;



    @BeforeEach
    void setUp() {
        createUserRequestDTO = new CreateUserRequestDTO("firatkil","firatkil@gmailcom","password");
        loginRequestDTO=new LoginRequestDTO("firatkil","firatkil@gmailcom","password");

        user=new User();
        user.setUsername("firatkil");
        user.setEmail("firatkil@gmailcom");
        user.setPassword("password");

    }

    @Test
    public void createUser_success(){
        Mockito.when(userRepository.existsByUsername(createUserRequestDTO.username())).thenReturn(false);
        Mockito.when(userRepository.existsByEmail(createUserRequestDTO.email())).thenReturn(false);
        Mockito.when(passwordEncoder.encode(createUserRequestDTO.password())).thenReturn("hashedPassword");
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        authService.createUser(createUserRequestDTO);

        Mockito.verify(userRepository).save(Mockito.any(User.class));
        Mockito.verify(profileService).createBlankProfileWithUser(user);

    }
    @Test
    public void createUser_whenUsernameExists_ShouldThrowException(){
        Mockito.when(userRepository.existsByUsername(createUserRequestDTO.username())).thenReturn(true);

        Assertions.assertThrows(UsernameAlreadyExistsException.class,()->authService.createUser(createUserRequestDTO));
    }

    @Test
    public void createUser_whenEmailExists_ShouldThrowException(){
        Mockito.when(userRepository.existsByUsername(createUserRequestDTO.username())).thenReturn(false);
        Mockito.when(userRepository.existsByEmail(createUserRequestDTO.email())).thenReturn(true);

        Assertions.assertThrows(EmailAlreadyExistsException.class,()->authService.createUser(createUserRequestDTO));


    }

    @Test
    public void loginUser_success_WhenUsernameProvided(){

        Mockito.when(userRepository.findByUsername(loginRequestDTO.username())).thenReturn(Optional.of(user));
        Mockito.when(passwordEncoder.matches(loginRequestDTO.password(), "password")).thenReturn(true);
        Mockito.when(jwtService.generateToken(user.getUsername())).thenReturn("token");

        LoginResponseDTO loginResponseDTO = authService.loginUser(loginRequestDTO);

        Assertions.assertEquals("token",loginResponseDTO.token());
        Assertions.assertEquals(user.getUsername(),loginResponseDTO.username());
        Assertions.assertEquals(user.getEmail(),loginResponseDTO.email());


    }

    @Test
    public void loginUser_success_whenUsernameNotProvidedButEmailIsProvided(){
        LoginRequestDTO loginRequest=new LoginRequestDTO(null, loginRequestDTO.email(), loginRequestDTO.password());

        Mockito.when(userRepository.findByEmail(loginRequest.email())).thenReturn(Optional.of(user));
        Mockito.when(passwordEncoder.matches(loginRequest.password(), "password")).thenReturn(true);
        Mockito.when(jwtService.generateToken(user.getUsername())).thenReturn("token");

        LoginResponseDTO loginResponseDTO = authService.loginUser(loginRequest);

        Assertions.assertEquals("token",loginResponseDTO.token());
        Assertions.assertEquals(user.getUsername(),loginResponseDTO.username());
        Assertions.assertEquals(user.getEmail(),loginResponseDTO.email());


    }
    @Test
    public void loginUser_ShouldThrowExceptionWhenUserNotFoundWithUsername(){

        Mockito.when(userRepository.findByUsername(loginRequestDTO.username())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,()->authService.loginUser(loginRequestDTO));
    }

    @Test
    public void loginUser_ShouldThrowExceptionWhenUserNotFoundWithEmail(){
        LoginRequestDTO loginRequest=new LoginRequestDTO(null, loginRequestDTO.email(), loginRequestDTO.password());
        Mockito.when(userRepository.findByEmail(loginRequest.email())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,()->authService.loginUser(loginRequest));

    }

    @Test
    public void loginUser_ShouldThrowExceptionWhenPasswordsDoesntMatch(){
        Mockito.when(userRepository.findByUsername(loginRequestDTO.username())).thenReturn(Optional.of(user));
        Mockito.when(passwordEncoder.matches(loginRequestDTO.password(), "password")).thenReturn(false);

        assertThrows(WrongCredentialsException.class,()->authService.loginUser(loginRequestDTO));


    }




    @Test
    public void loginUser_ShouldThrowExceptionWhenEmailAndUsernameNotProvided(){
        LoginRequestDTO loginRequest=new LoginRequestDTO(null,null, loginRequestDTO.password());

        assertThrows(IllegalArgumentException.class,()->authService.loginUser(loginRequest));

    }







}