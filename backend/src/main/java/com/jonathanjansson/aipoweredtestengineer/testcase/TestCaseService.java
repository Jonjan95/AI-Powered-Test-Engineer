package com.jonathanjansson.aipoweredtestengineer.testcase;

import com.jonathanjansson.aipoweredtestengineer.ai.AiService;
import com.jonathanjansson.aipoweredtestengineer.ai.AiUserStory;
import com.jonathanjansson.aipoweredtestengineer.ai.GeneratedTestCase;
import com.jonathanjansson.aipoweredtestengineer.model.TestCase;
import com.jonathanjansson.aipoweredtestengineer.model.UserStory;
import com.jonathanjansson.aipoweredtestengineer.repository.TestCaseRepository;
import com.jonathanjansson.aipoweredtestengineer.repository.UserStoryRepository;
import com.jonathanjansson.aipoweredtestengineer.userstory.UserStoryNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class TestCaseService {

    private final UserStoryRepository userStoryRepository;
    private final TestCaseRepository testCaseRepository;
    private final AiService aiService;

    public TestCaseService(
            UserStoryRepository userStoryRepository,
            TestCaseRepository testCaseRepository,
            AiService aiService
    ) {
        this.userStoryRepository = userStoryRepository;
        this.testCaseRepository = testCaseRepository;
        this.aiService = aiService;
    }

    @Transactional
    public List<TestCaseResponse> generate(UUID userStoryId) {
        UserStory userStory = findUserStory(userStoryId);
        AiUserStory aiUserStory = new AiUserStory(
                userStory.getTitle(),
                userStory.getDescription(),
                userStory.getAcceptanceCriteria()
        );

        List<TestCase> testCases = aiService.generateTestCases(aiUserStory).testCases().stream()
                .map(generated -> toEntity(userStory, generated))
                .toList();

        return testCaseRepository.saveAll(testCases).stream()
                .map(TestCaseService::toResponse)
                .toList();
    }

    public List<TestCaseResponse> findAllByUserStory(UUID userStoryId) {
        findUserStory(userStoryId);
        return testCaseRepository.findAllByUserStory_IdOrderByCreatedAtAsc(userStoryId).stream()
                .map(TestCaseService::toResponse)
                .toList();
    }

    private UserStory findUserStory(UUID id) {
        return userStoryRepository.findById(id)
                .orElseThrow(() -> new UserStoryNotFoundException(id));
    }

    private static TestCase toEntity(UserStory userStory, GeneratedTestCase generated) {
        return new TestCase(
                userStory,
                generated.title(),
                generated.preconditions(),
                generated.testSteps(),
                generated.expectedResult(),
                generated.testType()
        );
    }

    private static TestCaseResponse toResponse(TestCase testCase) {
        return new TestCaseResponse(
                testCase.getId(),
                testCase.getUserStory().getId(),
                testCase.getTitle(),
                testCase.getPreconditions(),
                testCase.getTestSteps(),
                testCase.getExpectedResult(),
                testCase.getTestType(),
                testCase.getCreatedAt()
        );
    }
}
