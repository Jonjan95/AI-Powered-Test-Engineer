package com.jonathanjansson.aipoweredtestengineer.ai;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OpenAiClientTest {

    @Test
    void reportsMissingApiKeyWithoutMakingARequest() {
        OpenAiClient client = createClient(new OpenAiProperties());

        assertFalse(client.isConfigured());
        assertThrows(
                AiGenerationException.class,
                () -> client.generateTestCases(new AiUserStory("Title", null, null))
        );
    }

    @Test
    void reportsConfiguredApiKeyWithoutExposingItsValue() {
        OpenAiProperties properties = new OpenAiProperties();
        properties.setApiKey("test-key");
        OpenAiClient client = createClient(properties);

        assertTrue(client.isConfigured());
    }

    @Test
    void parsesStructuredTestCases() throws Exception {
        OpenAiClient client = createClient(new OpenAiProperties());
        ObjectMapper objectMapper = new ObjectMapper();

        GeneratedTestCaseBatch batch = client.parseResponse(objectMapper.readTree("""
                {
                  "output": [{
                    "type": "message",
                    "content": [{
                      "type": "output_text",
                      "text": "{\\\"testCases\\\":[{\\\"title\\\":\\\"Successful login\\\",\\\"preconditions\\\":\\\"Registered user\\\",\\\"testSteps\\\":\\\"Enter valid credentials\\\",\\\"expectedResult\\\":\\\"Dashboard opens\\\",\\\"testType\\\":\\\"FUNCTIONAL\\\"}]}"
                    }]
                  }]
                }
                """));

        assertEquals(1, batch.testCases().size());
        assertEquals("Successful login", batch.testCases().getFirst().title());
    }

    @Test
    void rejectsRefusalResponse() throws Exception {
        OpenAiClient client = createClient(new OpenAiProperties());
        ObjectMapper objectMapper = new ObjectMapper();

        assertThrows(
                AiGenerationException.class,
                () -> client.parseResponse(objectMapper.readTree("""
                        {
                          "output": [{
                            "content": [{"type":"refusal","refusal":"Cannot complete request"}]
                          }]
                        }
                        """))
        );
    }

    private static OpenAiClient createClient(OpenAiProperties properties) {
        return new OpenAiClient(properties, new ObjectMapper(), RestClient.builder());
    }
}
