package com.jonathanjansson.aipoweredtestengineer.ai;

public interface AiClient {

    GeneratedTestCaseBatch generateTestCases(AiUserStory userStory);

    GeneratedPlaywrightTest generatePlaywrightTest(AiPlaywrightRequest request);
}
