package com.jonathanjansson.aipoweredtestengineer.playwright;

import java.time.OffsetDateTime;
import java.util.UUID;

public record PlaywrightTestResponse(
        UUID id,
        UUID userStoryId,
        String fileName,
        String scriptContent,
        OffsetDateTime createdAt
) {
}
