package com.jonathanjansson.aipoweredtestengineer.userstory;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserStoryNotFoundException extends RuntimeException {

    public UserStoryNotFoundException(UUID id) {
        super("User story not found: " + id);
    }
}
