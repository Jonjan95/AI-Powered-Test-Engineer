package com.jonathanjansson.aipoweredtestengineer.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user_stories")
public class UserStory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "acceptance_criteria", columnDefinition = "text")
    private String acceptanceCriteria;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @OneToMany(mappedBy = "userStory")
    private List<TestCase> testCases = new ArrayList<>();

    protected UserStory() {
    }

    public UserStory(Project project, String title, String description, String acceptanceCriteria) {
        this.project = project;
        this.title = title;
        this.description = description;
        this.acceptanceCriteria = acceptanceCriteria;
    }

    @PrePersist
    void onCreate() {
        createdAt = OffsetDateTime.now(ZoneOffset.UTC);
    }

    public void update(String title, String description, String acceptanceCriteria) {
        this.title = title;
        this.description = description;
        this.acceptanceCriteria = acceptanceCriteria;
    }

    public UUID getId() {
        return id;
    }

    public Project getProject() {
        return project;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getAcceptanceCriteria() {
        return acceptanceCriteria;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public List<TestCase> getTestCases() {
        return Collections.unmodifiableList(testCases);
    }
}
