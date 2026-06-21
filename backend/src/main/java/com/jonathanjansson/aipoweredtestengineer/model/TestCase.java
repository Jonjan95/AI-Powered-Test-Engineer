package com.jonathanjansson.aipoweredtestengineer.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Entity
@Table(name = "test_cases")
public class TestCase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_story_id", nullable = false)
    private UserStory userStory;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, columnDefinition = "text")
    private String preconditions;

    @Column(name = "test_steps", nullable = false, columnDefinition = "text")
    private String testSteps;

    @Column(name = "expected_result", nullable = false, columnDefinition = "text")
    private String expectedResult;

    @Column(name = "test_type", nullable = false, length = 100)
    private String testType;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    protected TestCase() {
    }

    public TestCase(
            UserStory userStory,
            String title,
            String preconditions,
            String testSteps,
            String expectedResult,
            String testType
    ) {
        this.userStory = userStory;
        this.title = title;
        this.preconditions = preconditions;
        this.testSteps = testSteps;
        this.expectedResult = expectedResult;
        this.testType = testType;
    }

    @PrePersist
    void onCreate() {
        createdAt = OffsetDateTime.now(ZoneOffset.UTC);
    }

    public UUID getId() {
        return id;
    }

    public UserStory getUserStory() {
        return userStory;
    }

    public String getTitle() {
        return title;
    }

    public String getPreconditions() {
        return preconditions;
    }

    public String getTestSteps() {
        return testSteps;
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    public String getTestType() {
        return testType;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }
}
