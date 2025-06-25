package com.worksOnLocal.DevLink.dto.response;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UpdateProjectResponseDTO(

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
