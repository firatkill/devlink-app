package com.worksOnLocal.DevLink.dto.response;

public record UpdateExternalLinksResponseDTO(
        Long id,
        String icon,
        String title,
        String url,
        boolean hidden
) {
}
