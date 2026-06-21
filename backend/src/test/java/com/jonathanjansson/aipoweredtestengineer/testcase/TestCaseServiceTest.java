package com.jonathanjansson.aipoweredtestengineer.testcase;

import com.jonathanjansson.aipoweredtestengineer.ai.AiService;
import com.jonathanjansson.aipoweredtestengineer.ai.AiUserStory;
import com.jonathanjansson.aipoweredtestengineer.ai.GeneratedTestCase;
import com.jonathanjansson.aipoweredtestengineer.ai.GeneratedTestCaseBatch;
import com.jonathanjansson.aipoweredtestengineer.model.Project;
import com.jonathanjansson.aipoweredtestengineer.model.TestCase;
import com.jonathanjansson.aipoweredtestengineer.model.UserStory;
import com.jonathanjansson.aipoweredtestengineer.repository.TestCaseRepository;
import com.jonathanjansson.aipoweredtestengineer.repository.UserStoryRepository;
import com.jonathanjansson.aipoweredtestengineer.userstory.UserStoryNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TestCaseServiceTest {

    @Mock
    private UserStoryRepository userStoryRepository;

    @Mock
    private TestCaseRepository testCaseRepository;

    @Mock
    private AiService aiService;

    @InjectMocks
    private TestCaseService testCaseService;

    @Test
    void generateSavesStructuredAiResults() {
        UUID userStoryId = UUID.randomUUID();
        UserStory userStory = userStory();
        GeneratedTestCase generated = new GeneratedTestCase(
                "Successful login",
                "Registered user",
                "Enter valid credentials",
                "Dashboard opens",
                "FUNCTIONAL"
        );
        when(userStoryRepository.findById(userStoryId)).thenReturn(Optional.of(userStory));
        when(aiService.generateTestCases(any(AiUserStory.class)))
                .thenReturn(new GeneratedTestCaseBatch(List.of(generated)));
        when(testCaseRepository.saveAll(any())).thenAnswer(invocation -> invocation.getArgument(0));

        List<TestCaseResponse> responses = testCaseService.generate(userStoryId);

        assertEquals(1, responses.size());
        assertEquals("Successful login", responses.getFirst().title());
        assertEquals("Dashboard opens", responses.getFirst().expectedResult());
        verify(testCaseRepository).saveAll(any());
    }

    @Test
    void generateDoesNotCallAiWhenUserStoryIsMissing() {
        UUID userStoryId = UUID.randomUUID();
        when(userStoryRepository.findById(userStoryId)).thenReturn(Optional.empty());

        assertThrows(UserStoryNotFoundException.class, () -> testCaseService.generate(userStoryId));
        verify(aiService, never()).generateTestCases(any());
    }

    @Test
    void findAllReturnsStoredTestCases() {
        UUID userStoryId = UUID.randomUUID();
        UserStory userStory = userStory();
        TestCase testCase = new TestCase(
                userStory,
                "Successful login",
                "Registered user",
                "Enter valid credentials",
                "Dashboard opens",
                "FUNCTIONAL"
        );
        when(userStoryRepository.findById(userStoryId)).thenReturn(Optional.of(userStory));
        when(testCaseRepository.findAllByUserStory_IdOrderByCreatedAtAsc(userStoryId))
                .thenReturn(List.of(testCase));

        List<TestCaseResponse> responses = testCaseService.findAllByUserStory(userStoryId);

        assertEquals(1, responses.size());
        assertEquals("FUNCTIONAL", responses.getFirst().testType());
    }

    private static UserStory userStory() {
        return new UserStory(
                new Project("Project", null),
                "Login",
                "Users can log in",
                "Valid credentials open the dashboard"
        );
    }
}
