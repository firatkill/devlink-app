package com.worksOnLocal.DevLink.dto.response;

import java.util.List;

public record CreateProjectResponseDTO(

        Long id,
        String image,
        String name,
        String description,
        String deploymentUrl,
        String sourceCodeUrl,
        List<String> techsUsed,
        boolean hidden
) {
}
