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
@Table(name = "playwright_tests")
public class PlaywrightTest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_story_id", nullable = false)
    private UserStory userStory;

    @Column(name = "file_name", nullable = false, length = 255)
    private String fileName;

    @Column(name = "script_content", nullable = false, columnDefinition = "text")
    private String scriptContent;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    protected PlaywrightTest() {
    }

    public PlaywrightTest(UserStory userStory, String fileName, String scriptContent) {
        this.userStory = userStory;
        this.fileName = fileName;
        this.scriptContent = scriptContent;
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

    public String getFileName() {
        return fileName;
    }

    public String getScriptContent() {
        return scriptContent;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }
}
