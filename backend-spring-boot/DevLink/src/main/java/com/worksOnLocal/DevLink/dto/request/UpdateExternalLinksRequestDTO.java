package com.worksOnLocal.DevLink.dto.request;

import com.worksOnLocal.DevLink.entity.ExternalLink;

public record UpdateExternalLinksRequestDTO(
        String icon,
        String title,
        String url,
        boolean hidden
) {
}
