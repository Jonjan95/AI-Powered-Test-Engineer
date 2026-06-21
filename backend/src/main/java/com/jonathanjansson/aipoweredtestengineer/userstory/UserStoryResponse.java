package com.jonathanjansson.aipoweredtestengineer.userstory;

import java.time.OffsetDateTime;
import java.util.UUID;

public record UserStoryResponse(
        UUID id,
        UUID projectId,
        String title,
        String description,
        String acceptanceCriteria,
        OffsetDateTime createdAt
) {
}
