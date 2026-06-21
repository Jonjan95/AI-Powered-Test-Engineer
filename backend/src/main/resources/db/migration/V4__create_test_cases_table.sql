CREATE TABLE test_cases (
    id UUID PRIMARY KEY,
    user_story_id UUID NOT NULL,
    title VARCHAR(255) NOT NULL,
    preconditions TEXT NOT NULL,
    test_steps TEXT NOT NULL,
    expected_result TEXT NOT NULL,
    test_type VARCHAR(100) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT fk_test_cases_user_story
        FOREIGN KEY (user_story_id) REFERENCES user_stories (id) ON DELETE CASCADE
);

CREATE INDEX idx_test_cases_user_story_id ON test_cases (user_story_id);
