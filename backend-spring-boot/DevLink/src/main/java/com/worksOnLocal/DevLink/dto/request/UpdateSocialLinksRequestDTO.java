package com.worksOnLocal.DevLink.dto.request;

import com.worksOnLocal.DevLink.enums.Platform;

public record UpdateSocialLinksRequestDTO(
        String url,
        Platform platform,
        boolean hidden
) {


}
