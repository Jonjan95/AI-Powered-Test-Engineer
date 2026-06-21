package com.jonathanjansson.aipoweredtestengineer.repository;

import com.jonathanjansson.aipoweredtestengineer.model.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TestCaseRepository extends JpaRepository<TestCase, UUID> {

    List<TestCase> findAllByUserStory_IdOrderByCreatedAtAsc(UUID userStoryId);
}
