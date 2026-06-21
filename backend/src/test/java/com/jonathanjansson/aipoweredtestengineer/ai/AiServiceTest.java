package com.jonathanjansson.aipoweredtestengineer.ai;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AiServiceTest {

    @Mock
    private AiClient aiClient;

    @InjectMocks
    private AiService aiService;

    @Test
    void generateTestCasesDelegatesToAiClient() {
        String userStory = "As a user, I want to log in";
        String generatedTestCases = "Generated test cases";
        when(aiClient.generateTestCases(userStory)).thenReturn(generatedTestCases);

        String result = aiService.generateTestCases(userStory);

        assertEquals(generatedTestCases, result);
        verify(aiClient).generateTestCases(userStory);
    }
}
