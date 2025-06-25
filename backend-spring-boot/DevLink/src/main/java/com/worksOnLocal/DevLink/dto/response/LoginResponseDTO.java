package com.worksOnLocal.DevLink.dto.response;

public record LoginResponseDTO(
        String username,
        String email,
        String token
) {
}
