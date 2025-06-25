package com.worksOnLocal.DevLink.dto.request;

import com.worksOnLocal.DevLink.entity.Profile;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UpdateProjectRequestDTO(
        @NotBlank
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
