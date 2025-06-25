package com.worksOnLocal.DevLink.dto.request;

import com.worksOnLocal.DevLink.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CreateUserRequestDTO(
        @Length(min = 3,max=24)
        @NotBlank
        String username,
        @NotBlank
        @Email
        String email
        ,
        @NotBlank
        @Length(min = 4 )
        String password
) {


}
