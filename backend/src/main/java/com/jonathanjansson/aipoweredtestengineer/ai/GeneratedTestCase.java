package com.jonathanjansson.aipoweredtestengineer.ai;

public record GeneratedTestCase(
        String title,
        String preconditions,
        String testSteps,
        String expectedResult,
        String testType
) {
}
