package com.worksOnLocal.DevLink.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record CreateProjectRequestDTO(


        String image,
        String name,
        String description,
        String deploymentUrl,
        String sourceCodeUrl,
        List<String> techsUsed,
        boolean hidden
) {
}
