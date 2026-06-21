package com.jonathanjansson.aipoweredtestengineer.playwright;

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

@WebMvcTest(PlaywrightTestController.class)
class PlaywrightTestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PlaywrightTestService playwrightTestService;

    @Test
    void generateReturnsCreatedPlaywrightTest() throws Exception {
        UUID userStoryId = UUID.randomUUID();
        UUID playwrightTestId = UUID.randomUUID();
        when(playwrightTestService.generate(userStoryId))
                .thenReturn(response(playwrightTestId, userStoryId));

        mockMvc.perform(post(
                        "/api/user-stories/{userStoryId}/playwright-tests/generate",
                        userStoryId
                ))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(playwrightTestId.toString()))
                .andExpect(jsonPath("$.userStoryId").value(userStoryId.toString()))
                .andExpect(jsonPath("$.fileName").value("login.spec.ts"));
    }

    @Test
    void findAllReturnsStoredPlaywrightTests() throws Exception {
        UUID userStoryId = UUID.randomUUID();
        UUID playwrightTestId = UUID.randomUUID();
        when(playwrightTestService.findAllByUserStory(userStoryId))
                .thenReturn(List.of(response(playwrightTestId, userStoryId)));

        mockMvc.perform(get("/api/user-stories/{userStoryId}/playwright-tests", userStoryId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(playwrightTestId.toString()))
                .andExpect(jsonPath("$[0].scriptContent").value("test('login', () => {});"));
    }

    @Test
    void findByIdReturnsStoredPlaywrightTest() throws Exception {
        UUID userStoryId = UUID.randomUUID();
        UUID playwrightTestId = UUID.randomUUID();
        when(playwrightTestService.findById(playwrightTestId))
                .thenReturn(response(playwrightTestId, userStoryId));

        mockMvc.perform(get("/api/playwright-tests/{id}", playwrightTestId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(playwrightTestId.toString()))
                .andExpect(jsonPath("$.fileName").value("login.spec.ts"));
    }

    private static PlaywrightTestResponse response(UUID id, UUID userStoryId) {
        return new PlaywrightTestResponse(
                id,
                userStoryId,
                "login.spec.ts",
                "test('login', () => {});",
                OffsetDateTime.now()
        );
    }
}
