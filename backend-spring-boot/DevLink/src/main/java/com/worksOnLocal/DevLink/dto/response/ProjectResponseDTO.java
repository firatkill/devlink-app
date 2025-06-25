package com.worksOnLocal.DevLink.dto.response;

import com.worksOnLocal.DevLink.entity.Profile;
import jakarta.persistence.Column;

import java.util.List;

public record ProjectResponseDTO(
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
