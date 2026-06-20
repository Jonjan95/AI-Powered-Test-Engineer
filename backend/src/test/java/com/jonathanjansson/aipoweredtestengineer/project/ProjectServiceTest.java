package com.jonathanjansson.aipoweredtestengineer.project;

import com.jonathanjansson.aipoweredtestengineer.model.Project;
import com.jonathanjansson.aipoweredtestengineer.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

    @Test
    void updateChangesExistingProject() {
        UUID id = UUID.randomUUID();
        Project project = new Project("Old name", "Old description");
        when(projectRepository.findById(id)).thenReturn(Optional.of(project));

        ProjectResponse response = projectService.update(
                id,
                new ProjectRequest("New name", "New description")
        );

        assertEquals("New name", response.name());
        assertEquals("New description", response.description());
    }

    @Test
    void findByIdThrowsWhenProjectDoesNotExist() {
        UUID id = UUID.randomUUID();
        when(projectRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ProjectNotFoundException.class, () -> projectService.findById(id));
    }

    @Test
    void deleteRemovesExistingProject() {
        UUID id = UUID.randomUUID();
        Project project = new Project("Project", null);
        when(projectRepository.findById(id)).thenReturn(Optional.of(project));

        projectService.delete(id);

        verify(projectRepository).delete(project);
    }
}
