package com.worksOnLocal.DevLink.dto.request;

public record UpdateProfileHeaderRequestDTO(
        String displayName,
        String headerTitle,
        String headerDescription
) {
}
