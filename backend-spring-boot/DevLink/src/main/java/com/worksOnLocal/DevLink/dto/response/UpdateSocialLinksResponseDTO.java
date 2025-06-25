package com.worksOnLocal.DevLink.dto.response;

import com.worksOnLocal.DevLink.enums.Platform;

public record UpdateSocialLinksResponseDTO(
        Long id,
        String url,
        Platform platform,
        boolean hidden
) {
}
