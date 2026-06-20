package com.jonathanjansson.aipoweredtestengineer.project;

import com.jonathanjansson.aipoweredtestengineer.model.Project;
import com.jonathanjansson.aipoweredtestengineer.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Transactional
    public ProjectResponse create(ProjectRequest request) {
        Project project = new Project(request.name(), request.description());
        return toResponse(projectRepository.save(project));
    }

    public List<ProjectResponse> findAll() {
        return projectRepository.findAll().stream()
                .map(ProjectService::toResponse)
                .toList();
    }

    public ProjectResponse findById(UUID id) {
        return toResponse(findProject(id));
    }

    @Transactional
    public ProjectResponse update(UUID id, ProjectRequest request) {
        Project project = findProject(id);
        project.update(request.name(), request.description());
        return toResponse(project);
    }

    @Transactional
    public void delete(UUID id) {
        Project project = findProject(id);
        projectRepository.delete(project);
    }

    private Project findProject(UUID id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id));
    }

    private static ProjectResponse toResponse(Project project) {
        return new ProjectResponse(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getCreatedAt(),
                project.getUpdatedAt()
        );
    }
}
