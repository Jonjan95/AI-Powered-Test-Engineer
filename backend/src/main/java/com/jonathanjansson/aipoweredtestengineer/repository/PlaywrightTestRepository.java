package com.jonathanjansson.aipoweredtestengineer.repository;

import com.jonathanjansson.aipoweredtestengineer.model.PlaywrightTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PlaywrightTestRepository extends JpaRepository<PlaywrightTest, UUID> {

    List<PlaywrightTest> findAllByUserStory_IdOrderByCreatedAtAsc(UUID userStoryId);
}
