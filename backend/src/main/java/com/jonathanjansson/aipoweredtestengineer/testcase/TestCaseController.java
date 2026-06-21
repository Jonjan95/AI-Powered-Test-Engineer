package com.jonathanjansson.aipoweredtestengineer.testcase;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user-stories/{userStoryId}/test-cases")
public class TestCaseController {

    private final TestCaseService testCaseService;

    public TestCaseController(TestCaseService testCaseService) {
        this.testCaseService = testCaseService;
    }

    @PostMapping("/generate")
    @ResponseStatus(HttpStatus.CREATED)
    public List<TestCaseResponse> generate(@PathVariable UUID userStoryId) {
        return testCaseService.generate(userStoryId);
    }

    @GetMapping
    public List<TestCaseResponse> findAllByUserStory(@PathVariable UUID userStoryId) {
        return testCaseService.findAllByUserStory(userStoryId);
    }
}
