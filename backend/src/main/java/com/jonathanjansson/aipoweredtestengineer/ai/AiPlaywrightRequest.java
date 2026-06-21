package com.jonathanjansson.aipoweredtestengineer.ai;

import java.util.List;

public record AiPlaywrightRequest(AiUserStory userStory, List<AiTestCase> testCases) {
}
