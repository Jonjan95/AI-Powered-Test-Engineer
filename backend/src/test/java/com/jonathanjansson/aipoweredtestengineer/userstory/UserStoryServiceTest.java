package com.jonathanjansson.aipoweredtestengineer.userstory;

import com.jonathanjansson.aipoweredtestengineer.model.Project;
import com.jonathanjansson.aipoweredtestengineer.model.UserStory;
import com.jonathanjansson.aipoweredtestengineer.project.ProjectNotFoundException;
import com.jonathanjansson.aipoweredtestengineer.repository.ProjectRepository;
import com.jonathanjansson.aipoweredtestengineer.repository.UserStoryRepository;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserStoryServiceTest {

    @Mock
    private UserStoryRepository userStoryRepository;

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private UserStoryService userStoryService;

    @Test
    void findAllReturnsStoriesForExistingProject() {
        UUID projectId = UUID.randomUUID();
        Project project = new Project("Project", null);
        UserStory story = new UserStory(project, "Login", "Description", "Criteria");
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(userStoryRepository.findAllByProject_IdOrderByCreatedAtAsc(projectId))
                .thenReturn(List.of(story));

        List<UserStoryResponse> response = userStoryService.findAllByProject(projectId);

        assertEquals(1, response.size());
        assertEquals("Login", response.getFirst().title());
    }

    @Test
    void findAllThrowsWhenProjectDoesNotExist() {
        UUID projectId = UUID.randomUUID();
        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        assertThrows(
                ProjectNotFoundException.class,
                () -> userStoryService.findAllByProject(projectId)
        );
    }

    @Test
    void updateChangesExistingUserStory() {
        UUID id = UUID.randomUUID();
        UserStory story = new UserStory(
                new Project("Project", null),
                "Old title",
                "Old description",
                "Old criteria"
        );
        when(userStoryRepository.findById(id)).thenReturn(Optional.of(story));

        UserStoryResponse response = userStoryService.update(
                id,
                new UserStoryRequest("New title", "New description", "New criteria")
        );

        assertEquals("New title", response.title());
        assertEquals("New description", response.description());
        assertEquals("New criteria", response.acceptanceCriteria());
    }

    @Test
    void findByIdThrowsWhenUserStoryDoesNotExist() {
        UUID id = UUID.randomUUID();
        when(userStoryRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserStoryNotFoundException.class, () -> userStoryService.findById(id));
    }

    @Test
    void deleteRemovesExistingUserStory() {
        UUID id = UUID.randomUUID();
        UserStory story = new UserStory(
                new Project("Project", null),
                "Title",
                null,
                null
        );
        when(userStoryRepository.findById(id)).thenReturn(Optional.of(story));

        userStoryService.delete(id);

        verify(userStoryRepository).delete(story);
    }
}
