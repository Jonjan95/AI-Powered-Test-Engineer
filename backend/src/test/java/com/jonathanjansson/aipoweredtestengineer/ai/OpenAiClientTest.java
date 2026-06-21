package com.jonathanjansson.aipoweredtestengineer.ai;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OpenAiClientTest {

    @Test
    void reportsMissingApiKeyWithoutMakingARequest() {
        OpenAiClient client = new OpenAiClient(new OpenAiProperties());

        assertFalse(client.isConfigured());
        assertThrows(
                UnsupportedOperationException.class,
                () -> client.generateTestCases("User story")
        );
    }

    @Test
    void reportsConfiguredApiKeyWithoutExposingItsValue() {
        OpenAiProperties properties = new OpenAiProperties();
        properties.setApiKey("test-key");
        OpenAiClient client = new OpenAiClient(properties);

        assertTrue(client.isConfigured());
    }
}
