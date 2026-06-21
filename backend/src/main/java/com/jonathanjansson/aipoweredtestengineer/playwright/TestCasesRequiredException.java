package com.jonathanjansson.aipoweredtestengineer.playwright;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.CONFLICT)
public class TestCasesRequiredException extends RuntimeException {

    public TestCasesRequiredException(UUID userStoryId) {
        super("Generate test cases before generating a Playwright test for user story: " + userStoryId);
    }
}
