package com.jonathanjansson.aipoweredtestengineer.playwright;

import com.jonathanjansson.aipoweredtestengineer.ai.AiPlaywrightRequest;
import com.jonathanjansson.aipoweredtestengineer.ai.AiService;
import com.jonathanjansson.aipoweredtestengineer.ai.AiTestCase;
import com.jonathanjansson.aipoweredtestengineer.ai.AiUserStory;
import com.jonathanjansson.aipoweredtestengineer.ai.GeneratedPlaywrightTest;
import com.jonathanjansson.aipoweredtestengineer.model.PlaywrightTest;
import com.jonathanjansson.aipoweredtestengineer.model.TestCase;
import com.jonathanjansson.aipoweredtestengineer.model.UserStory;
import com.jonathanjansson.aipoweredtestengineer.repository.PlaywrightTestRepository;
import com.jonathanjansson.aipoweredtestengineer.repository.TestCaseRepository;
import com.jonathanjansson.aipoweredtestengineer.repository.UserStoryRepository;
import com.jonathanjansson.aipoweredtestengineer.userstory.UserStoryNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class PlaywrightTestService {

    private final UserStoryRepository userStoryRepository;
    private final TestCaseRepository testCaseRepository;
    private final PlaywrightTestRepository playwrightTestRepository;
    private final AiService aiService;

    public PlaywrightTestService(
            UserStoryRepository userStoryRepository,
            TestCaseRepository testCaseRepository,
            PlaywrightTestRepository playwrightTestRepository,
            AiService aiService
    ) {
        this.userStoryRepository = userStoryRepository;
        this.testCaseRepository = testCaseRepository;
        this.playwrightTestRepository = playwrightTestRepository;
        this.aiService = aiService;
    }

    @Transactional
    public PlaywrightTestResponse generate(UUID userStoryId) {
        UserStory userStory = findUserStory(userStoryId);
        List<TestCase> testCases = testCaseRepository
                .findAllByUserStory_IdOrderByCreatedAtAsc(userStoryId);
        if (testCases.isEmpty()) {
            throw new TestCasesRequiredException(userStoryId);
        }

        AiPlaywrightRequest request = new AiPlaywrightRequest(
                new AiUserStory(
                        userStory.getTitle(),
                        userStory.getDescription(),
                        userStory.getAcceptanceCriteria()
                ),
                testCases.stream().map(PlaywrightTestService::toAiTestCase).toList()
        );
        GeneratedPlaywrightTest generated = aiService.generatePlaywrightTest(request);
        PlaywrightTest saved = playwrightTestRepository.save(new PlaywrightTest(
                userStory,
                generated.fileName(),
                generated.scriptContent()
        ));
        return toResponse(saved);
    }

    public List<PlaywrightTestResponse> findAllByUserStory(UUID userStoryId) {
        findUserStory(userStoryId);
        return playwrightTestRepository.findAllByUserStory_IdOrderByCreatedAtAsc(userStoryId).stream()
                .map(PlaywrightTestService::toResponse)
                .toList();
    }

    public PlaywrightTestResponse findById(UUID id) {
        return playwrightTestRepository.findById(id)
                .map(PlaywrightTestService::toResponse)
                .orElseThrow(() -> new PlaywrightTestNotFoundException(id));
    }

    private UserStory findUserStory(UUID id) {
        return userStoryRepository.findById(id)
                .orElseThrow(() -> new UserStoryNotFoundException(id));
    }

    private static AiTestCase toAiTestCase(TestCase testCase) {
        return new AiTestCase(
                testCase.getTitle(),
                testCase.getPreconditions(),
                testCase.getTestSteps(),
                testCase.getExpectedResult(),
                testCase.getTestType()
        );
    }

    private static PlaywrightTestResponse toResponse(PlaywrightTest playwrightTest) {
        return new PlaywrightTestResponse(
                playwrightTest.getId(),
                playwrightTest.getUserStory().getId(),
                playwrightTest.getFileName(),
                playwrightTest.getScriptContent(),
                playwrightTest.getCreatedAt()
        );
    }
}
