package com.jonathanjansson.aipoweredtestengineer.ai;

import org.springframework.stereotype.Service;

@Service
public class AiService {

    private final AiClient aiClient;

    public AiService(AiClient aiClient) {
        this.aiClient = aiClient;
    }

    public String generateTestCases(String userStory) {
        return aiClient.generateTestCases(userStory);
    }
}
