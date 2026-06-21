CREATE TABLE playwright_tests (
    id UUID PRIMARY KEY,
    user_story_id UUID NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    script_content TEXT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT fk_playwright_tests_user_story
        FOREIGN KEY (user_story_id) REFERENCES user_stories (id) ON DELETE CASCADE
);

CREATE INDEX idx_playwright_tests_user_story_id ON playwright_tests (user_story_id);
