package com.worksOnLocal.DevLink.dto.response;

public record UpdateUserResponseDTO(
        String username,
        String email,
        String token
) {
}
