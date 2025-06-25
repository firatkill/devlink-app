package com.worksOnLocal.DevLink.dto.response;

public record ExternalLinkResponseDTO(
        Long id,
        String icon,
        String title,
        String url,
        boolean hidden
) {
}
