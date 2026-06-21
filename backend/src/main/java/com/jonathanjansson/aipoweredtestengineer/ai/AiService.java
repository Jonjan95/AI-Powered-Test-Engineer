package com.jonathanjansson.aipoweredtestengineer.ai;

import org.springframework.stereotype.Service;

@Service
public class AiService {

    private final AiClient aiClient;

    public AiService(AiClient aiClient) {
        this.aiClient = aiClient;
    }

    public GeneratedTestCaseBatch generateTestCases(AiUserStory userStory) {
        return aiClient.generateTestCases(userStory);
    }
}
