package com.jonathanjansson.aipoweredtestengineer.playwright;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class PlaywrightTestController {

    private final PlaywrightTestService playwrightTestService;

    public PlaywrightTestController(PlaywrightTestService playwrightTestService) {
        this.playwrightTestService = playwrightTestService;
    }

    @PostMapping("/api/user-stories/{userStoryId}/playwright-tests/generate")
    @ResponseStatus(HttpStatus.CREATED)
    public PlaywrightTestResponse generate(@PathVariable UUID userStoryId) {
        return playwrightTestService.generate(userStoryId);
    }

    @GetMapping("/api/user-stories/{userStoryId}/playwright-tests")
    public List<PlaywrightTestResponse> findAllByUserStory(@PathVariable UUID userStoryId) {
        return playwrightTestService.findAllByUserStory(userStoryId);
    }

    @GetMapping("/api/playwright-tests/{id}")
    public PlaywrightTestResponse findById(@PathVariable UUID id) {
        return playwrightTestService.findById(id);
    }
}
