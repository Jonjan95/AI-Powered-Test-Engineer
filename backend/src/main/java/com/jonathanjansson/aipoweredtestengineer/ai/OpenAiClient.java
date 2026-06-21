package com.jonathanjansson.aipoweredtestengineer.ai;

import org.springframework.stereotype.Component;

@Component
public class OpenAiClient implements AiClient {

    private final OpenAiProperties properties;

    public OpenAiClient(OpenAiProperties properties) {
        this.properties = properties;
    }

    @Override
    public String generateTestCases(String userStory) {
        throw new UnsupportedOperationException("OpenAI test-case generation is not implemented yet");
    }

    public boolean isConfigured() {
        return properties.hasApiKey();
    }
}
