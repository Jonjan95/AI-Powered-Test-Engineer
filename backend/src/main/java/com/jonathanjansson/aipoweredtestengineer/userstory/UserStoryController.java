package com.jonathanjansson.aipoweredtestengineer.userstory;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class UserStoryController {

    private final UserStoryService userStoryService;

    public UserStoryController(UserStoryService userStoryService) {
        this.userStoryService = userStoryService;
    }

    @PostMapping("/projects/{projectId}/user-stories")
    @ResponseStatus(HttpStatus.CREATED)
    public UserStoryResponse create(
            @PathVariable UUID projectId,
            @Valid @RequestBody UserStoryRequest request
    ) {
        return userStoryService.create(projectId, request);
    }

    @GetMapping("/projects/{projectId}/user-stories")
    public List<UserStoryResponse> findAllByProject(@PathVariable UUID projectId) {
        return userStoryService.findAllByProject(projectId);
    }

    @GetMapping("/user-stories/{id}")
    public UserStoryResponse findById(@PathVariable UUID id) {
        return userStoryService.findById(id);
    }

    @PutMapping("/user-stories/{id}")
    public UserStoryResponse update(
            @PathVariable UUID id,
            @Valid @RequestBody UserStoryRequest request
    ) {
        return userStoryService.update(id, request);
    }

    @DeleteMapping("/user-stories/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        userStoryService.delete(id);
    }
}
