package com.jonathanjansson.aipoweredtestengineer.testcase;

import java.time.OffsetDateTime;
import java.util.UUID;

public record TestCaseResponse(
        UUID id,
        UUID userStoryId,
        String title,
        String preconditions,
        String testSteps,
        String expectedResult,
        String testType,
        OffsetDateTime createdAt
) {
}
