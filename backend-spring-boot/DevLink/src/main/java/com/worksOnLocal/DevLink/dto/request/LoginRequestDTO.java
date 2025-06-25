package com.worksOnLocal.DevLink.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record LoginRequestDTO(

        @Length(min = 3,max=24)
        String username,
        @Email
        String email,
        @Length(min = 4 )
        @NotBlank
        String password
) {

}
