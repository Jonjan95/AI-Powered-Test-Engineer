package com.jonathanjansson.aipoweredtestengineer.ai;

public record AiTestCase(
        String title,
        String preconditions,
        String testSteps,
        String expectedResult,
        String testType
) {
}
