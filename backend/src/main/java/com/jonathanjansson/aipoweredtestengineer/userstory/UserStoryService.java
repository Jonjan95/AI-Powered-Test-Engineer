package com.jonathanjansson.aipoweredtestengineer.userstory;

import com.jonathanjansson.aipoweredtestengineer.model.Project;
import com.jonathanjansson.aipoweredtestengineer.model.UserStory;
import com.jonathanjansson.aipoweredtestengineer.project.ProjectNotFoundException;
import com.jonathanjansson.aipoweredtestengineer.repository.ProjectRepository;
import com.jonathanjansson.aipoweredtestengineer.repository.UserStoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class UserStoryService {

    private final UserStoryRepository userStoryRepository;
    private final ProjectRepository projectRepository;

    public UserStoryService(UserStoryRepository userStoryRepository, ProjectRepository projectRepository) {
        this.userStoryRepository = userStoryRepository;
        this.projectRepository = projectRepository;
    }

    @Transactional
    public UserStoryResponse create(UUID projectId, UserStoryRequest request) {
        Project project = findProject(projectId);
        UserStory userStory = new UserStory(
                project,
                request.title(),
                request.description(),
                request.acceptanceCriteria()
        );
        return toResponse(userStoryRepository.save(userStory));
    }

    public List<UserStoryResponse> findAllByProject(UUID projectId) {
        findProject(projectId);
        return userStoryRepository.findAllByProject_IdOrderByCreatedAtAsc(projectId).stream()
                .map(UserStoryService::toResponse)
                .toList();
    }

    public UserStoryResponse findById(UUID id) {
        return toResponse(findUserStory(id));
    }

    @Transactional
    public UserStoryResponse update(UUID id, UserStoryRequest request) {
        UserStory userStory = findUserStory(id);
        userStory.update(request.title(), request.description(), request.acceptanceCriteria());
        return toResponse(userStory);
    }

    @Transactional
    public void delete(UUID id) {
        userStoryRepository.delete(findUserStory(id));
    }

    private Project findProject(UUID id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id));
    }

    private UserStory findUserStory(UUID id) {
        return userStoryRepository.findById(id)
                .orElseThrow(() -> new UserStoryNotFoundException(id));
    }

    private static UserStoryResponse toResponse(UserStory userStory) {
        return new UserStoryResponse(
                userStory.getId(),
                userStory.getProject().getId(),
                userStory.getTitle(),
                userStory.getDescription(),
                userStory.getAcceptanceCriteria(),
                userStory.getCreatedAt()
        );
    }
}
