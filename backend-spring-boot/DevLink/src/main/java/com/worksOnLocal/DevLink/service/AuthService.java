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
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final ProfileService profileService;

    public void createUser(@Valid CreateUserRequestDTO createUserRequestDTO) {
        // böyle bir username/email'e sahip biri var mı

        if(userRepository.existsByUsername(createUserRequestDTO.username())) throw new UsernameAlreadyExistsException();
        if(userRepository.existsByEmail(createUserRequestDTO.email())) throw new EmailAlreadyExistsException();

        User user=new User();
        user.setUsername(createUserRequestDTO.username());
        String hashedPassword= passwordEncoder.encode(createUserRequestDTO.password());
        user.setPassword(hashedPassword);
        user.setEmail(createUserRequestDTO.email());


        User savedUser=userRepository.save(user);
        profileService.createBlankProfileWithUser(savedUser);

    }

    public LoginResponseDTO loginUser(@Valid LoginRequestDTO loginRequestDTO) {
        // kullanıcı ismi verildiyse, kullanıcı adına göre var mı diye arat.
        // kullanıcı adı yok email varsa emaila göre ara.
        // passwordler matchleşiyormu?

        User user;

        if(loginRequestDTO.username() != null) user=userRepository.findByUsername(loginRequestDTO.username()).orElseThrow(()->new EntityNotFoundException("Kullanıcı Bulunamadı."));
        else if(loginRequestDTO.email()!=null) user= userRepository.findByEmail(loginRequestDTO.email()).orElseThrow(()-> new EntityNotFoundException("Kullanıcı Bulunamadı."));
        else throw new IllegalArgumentException();

        if(!passwordEncoder.matches(loginRequestDTO.password(), user.getPassword())) throw new WrongCredentialsException("Wrong Password.");

        String token=jwtService.generateToken(user.getUsername());
        return new LoginResponseDTO(user.getUsername(),user.getEmail(),token);
    }
}
