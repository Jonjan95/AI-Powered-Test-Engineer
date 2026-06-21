package com.jonathanjansson.aipoweredtestengineer.ai;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

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
        AiUserStory userStory = new AiUserStory("Login", "Users can log in", "Valid credentials work");
        GeneratedTestCaseBatch generatedTestCases = new GeneratedTestCaseBatch(List.of(
                new GeneratedTestCase(
                        "Successful login",
                        "Registered user",
                        "Enter valid credentials",
                        "Dashboard is displayed",
                        "FUNCTIONAL"
                )
        ));
        when(aiClient.generateTestCases(userStory)).thenReturn(generatedTestCases);

        GeneratedTestCaseBatch result = aiService.generateTestCases(userStory);

        assertEquals(generatedTestCases, result);
        verify(aiClient).generateTestCases(userStory);
    }
}
