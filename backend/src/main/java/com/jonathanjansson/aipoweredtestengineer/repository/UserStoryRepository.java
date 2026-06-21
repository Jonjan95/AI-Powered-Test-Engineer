package com.jonathanjansson.aipoweredtestengineer.repository;

import com.jonathanjansson.aipoweredtestengineer.model.UserStory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserStoryRepository extends JpaRepository<UserStory, UUID> {

    List<UserStory> findAllByProject_IdOrderByCreatedAtAsc(UUID projectId);
}
