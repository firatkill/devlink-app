package com.worksOnLocal.DevLink.dto.response;

public record UpdateProfileHeaderResponseDTO(
        String displayName,
        String headerTitle,
        String headerDescription
) {
}
