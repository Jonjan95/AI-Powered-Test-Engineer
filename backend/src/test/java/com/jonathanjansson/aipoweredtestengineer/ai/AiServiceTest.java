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

    @Test
    void generatePlaywrightTestDelegatesToAiClient() {
        AiPlaywrightRequest request = new AiPlaywrightRequest(
                new AiUserStory("Login", "Users can log in", "Valid credentials work"),
                List.of(new AiTestCase(
                        "Successful login",
                        "Registered user",
                        "Enter valid credentials",
                        "Dashboard is displayed",
                        "FUNCTIONAL"
                ))
        );
        GeneratedPlaywrightTest generated = new GeneratedPlaywrightTest(
                "login.spec.ts",
                "import { test, expect } from '@playwright/test';"
        );
        when(aiClient.generatePlaywrightTest(request)).thenReturn(generated);

        assertEquals(generated, aiService.generatePlaywrightTest(request));
        verify(aiClient).generatePlaywrightTest(request);
    }
}
