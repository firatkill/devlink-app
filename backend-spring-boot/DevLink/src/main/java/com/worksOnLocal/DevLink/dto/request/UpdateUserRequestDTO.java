package com.worksOnLocal.DevLink.dto.request;

public record UpdateUserRequestDTO(
        String username,
        String email,
        String password
) {
}
