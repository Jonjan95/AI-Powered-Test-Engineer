package com.jonathanjansson.aipoweredtestengineer.playwright;

import com.jonathanjansson.aipoweredtestengineer.ai.AiPlaywrightRequest;
import com.jonathanjansson.aipoweredtestengineer.ai.AiService;
import com.jonathanjansson.aipoweredtestengineer.ai.GeneratedPlaywrightTest;
import com.jonathanjansson.aipoweredtestengineer.model.PlaywrightTest;
import com.jonathanjansson.aipoweredtestengineer.model.Project;
import com.jonathanjansson.aipoweredtestengineer.model.TestCase;
import com.jonathanjansson.aipoweredtestengineer.model.UserStory;
import com.jonathanjansson.aipoweredtestengineer.repository.PlaywrightTestRepository;
import com.jonathanjansson.aipoweredtestengineer.repository.TestCaseRepository;
import com.jonathanjansson.aipoweredtestengineer.repository.UserStoryRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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
class PlaywrightTestServiceTest {

    @Mock
    private UserStoryRepository userStoryRepository;

    @Mock
    private TestCaseRepository testCaseRepository;

    @Mock
    private PlaywrightTestRepository playwrightTestRepository;

    @Mock
    private AiService aiService;

    @InjectMocks
    private PlaywrightTestService playwrightTestService;

    @org.junit.jupiter.api.Test
    void generateSendsStoryAndCasesToAiAndSavesCode() {
        UUID userStoryId = UUID.randomUUID();
        UserStory userStory = userStory();
        TestCase testCase = testCase(userStory);
        String code = "import { test, expect } from '@playwright/test';";
        when(userStoryRepository.findById(userStoryId)).thenReturn(Optional.of(userStory));
        when(testCaseRepository.findAllByUserStory_IdOrderByCreatedAtAsc(userStoryId))
                .thenReturn(List.of(testCase));
        when(aiService.generatePlaywrightTest(any(AiPlaywrightRequest.class)))
                .thenReturn(new GeneratedPlaywrightTest("login.spec.ts", code));
        when(playwrightTestRepository.save(any(PlaywrightTest.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        PlaywrightTestResponse response = playwrightTestService.generate(userStoryId);

        assertEquals("login.spec.ts", response.fileName());
        assertEquals(code, response.scriptContent());
        ArgumentCaptor<AiPlaywrightRequest> requestCaptor =
                ArgumentCaptor.forClass(AiPlaywrightRequest.class);
        verify(aiService).generatePlaywrightTest(requestCaptor.capture());
        assertEquals("Login", requestCaptor.getValue().userStory().title());
        assertEquals("Successful login", requestCaptor.getValue().testCases().getFirst().title());
        verify(playwrightTestRepository).save(any(PlaywrightTest.class));
    }

    @org.junit.jupiter.api.Test
    void generateRejectsUserStoryWithoutTestCases() {
        UUID userStoryId = UUID.randomUUID();
        when(userStoryRepository.findById(userStoryId)).thenReturn(Optional.of(userStory()));
        when(testCaseRepository.findAllByUserStory_IdOrderByCreatedAtAsc(userStoryId))
                .thenReturn(List.of());

        assertThrows(TestCasesRequiredException.class, () -> playwrightTestService.generate(userStoryId));
        verify(aiService, never()).generatePlaywrightTest(any());
        verify(playwrightTestRepository, never()).save(any());
    }

    @org.junit.jupiter.api.Test
    void findAllReturnsStoredTestsForUserStory() {
        UUID userStoryId = UUID.randomUUID();
        UserStory userStory = userStory();
        when(userStoryRepository.findById(userStoryId)).thenReturn(Optional.of(userStory));
        when(playwrightTestRepository.findAllByUserStory_IdOrderByCreatedAtAsc(userStoryId))
                .thenReturn(List.of(new PlaywrightTest(
                        userStory,
                        "login.spec.ts",
                        "test('login', () => {});"
                )));

        List<PlaywrightTestResponse> result = playwrightTestService.findAllByUserStory(userStoryId);

        assertEquals(1, result.size());
        assertEquals("login.spec.ts", result.getFirst().fileName());
    }

    @org.junit.jupiter.api.Test
    void findByIdRejectsMissingPlaywrightTest() {
        UUID id = UUID.randomUUID();
        when(playwrightTestRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(PlaywrightTestNotFoundException.class, () -> playwrightTestService.findById(id));
    }

    private static UserStory userStory() {
        return new UserStory(
                new Project("Project", null),
                "Login",
                "Users can log in",
                "Valid credentials open the dashboard"
        );
    }

    private static TestCase testCase(UserStory userStory) {
        return new TestCase(
                userStory,
                "Successful login",
                "Registered user",
                "Enter valid credentials",
                "Dashboard opens",
                "FUNCTIONAL"
        );
    }
}
