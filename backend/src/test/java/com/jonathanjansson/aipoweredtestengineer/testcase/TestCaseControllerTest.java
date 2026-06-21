package com.jonathanjansson.aipoweredtestengineer.testcase;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TestCaseController.class)
class TestCaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TestCaseService testCaseService;

    @Test
    void generateReturnsCreatedTestCases() throws Exception {
        UUID userStoryId = UUID.randomUUID();
        UUID testCaseId = UUID.randomUUID();
        when(testCaseService.generate(userStoryId))
                .thenReturn(List.of(response(testCaseId, userStoryId)));

        mockMvc.perform(post("/api/user-stories/{userStoryId}/test-cases/generate", userStoryId))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$[0].id").value(testCaseId.toString()))
                .andExpect(jsonPath("$[0].userStoryId").value(userStoryId.toString()))
                .andExpect(jsonPath("$[0].title").value("Successful login"));
    }

    @Test
    void findAllReturnsStoredTestCases() throws Exception {
        UUID userStoryId = UUID.randomUUID();
        UUID testCaseId = UUID.randomUUID();
        when(testCaseService.findAllByUserStory(userStoryId))
                .thenReturn(List.of(response(testCaseId, userStoryId)));

        mockMvc.perform(get("/api/user-stories/{userStoryId}/test-cases", userStoryId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(testCaseId.toString()))
                .andExpect(jsonPath("$[0].testType").value("FUNCTIONAL"));
    }

    private static TestCaseResponse response(UUID testCaseId, UUID userStoryId) {
        return new TestCaseResponse(
                testCaseId,
                userStoryId,
                "Successful login",
                "Registered user",
                "Enter valid credentials",
                "Dashboard opens",
                "FUNCTIONAL",
                OffsetDateTime.now()
        );
    }
}
