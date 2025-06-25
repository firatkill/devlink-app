package com.worksOnLocal.DevLink.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateProfileImageRequestDTO(
        @NotBlank
        String image
) {
}
