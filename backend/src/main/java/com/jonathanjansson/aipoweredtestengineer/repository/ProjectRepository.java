package com.jonathanjansson.aipoweredtestengineer.repository;

import com.jonathanjansson.aipoweredtestengineer.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
}
