package com.jonathanjansson.aipoweredtestengineer.userstory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserStoryController.class)
class UserStoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserStoryService userStoryService;

    @Test
    void createReturnsCreatedUserStory() throws Exception {
        UUID projectId = UUID.randomUUID();
        UUID storyId = UUID.randomUUID();
        OffsetDateTime createdAt = OffsetDateTime.now();
        when(userStoryService.create(eq(projectId), any(UserStoryRequest.class)))
                .thenReturn(response(storyId, projectId, "Login", createdAt));

        mockMvc.perform(post("/api/projects/{projectId}/user-stories", projectId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "title":"Login",
                                  "description":"Users can log in",
                                  "acceptanceCriteria":"Valid credentials open the dashboard"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(storyId.toString()))
                .andExpect(jsonPath("$.projectId").value(projectId.toString()))
                .andExpect(jsonPath("$.title").value("Login"));
    }

    @Test
    void createRejectsBlankTitle() throws Exception {
        mockMvc.perform(post("/api/projects/{projectId}/user-stories", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"title":" ","description":"Description"}
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findAllReturnsStoriesForProject() throws Exception {
        UUID projectId = UUID.randomUUID();
        UUID storyId = UUID.randomUUID();
        OffsetDateTime createdAt = OffsetDateTime.now();
        when(userStoryService.findAllByProject(projectId))
                .thenReturn(List.of(response(storyId, projectId, "Login", createdAt)));

        mockMvc.perform(get("/api/projects/{projectId}/user-stories", projectId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(storyId.toString()))
                .andExpect(jsonPath("$[0].title").value("Login"));
    }

    @Test
    void findByIdReturnsUserStory() throws Exception {
        UUID projectId = UUID.randomUUID();
        UUID storyId = UUID.randomUUID();
        OffsetDateTime createdAt = OffsetDateTime.now();
        when(userStoryService.findById(storyId))
                .thenReturn(response(storyId, projectId, "Login", createdAt));

        mockMvc.perform(get("/api/user-stories/{id}", storyId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(storyId.toString()));
    }

    @Test
    void updateReturnsUpdatedUserStory() throws Exception {
        UUID projectId = UUID.randomUUID();
        UUID storyId = UUID.randomUUID();
        OffsetDateTime createdAt = OffsetDateTime.now();
        when(userStoryService.update(eq(storyId), any(UserStoryRequest.class)))
                .thenReturn(response(storyId, projectId, "Updated login", createdAt));

        mockMvc.perform(put("/api/user-stories/{id}", storyId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"title":"Updated login","description":"Description"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated login"));
    }

    @Test
    void deleteReturnsNoContent() throws Exception {
        UUID storyId = UUID.randomUUID();

        mockMvc.perform(delete("/api/user-stories/{id}", storyId))
                .andExpect(status().isNoContent());

        verify(userStoryService).delete(storyId);
    }

    private static UserStoryResponse response(
            UUID storyId,
            UUID projectId,
            String title,
            OffsetDateTime createdAt
    ) {
        return new UserStoryResponse(
                storyId,
                projectId,
                title,
                "Users can log in",
                "Valid credentials open the dashboard",
                createdAt
        );
    }
}
