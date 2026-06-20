-- Flyway migration: create test_record table
CREATE TABLE IF NOT EXISTS test_record (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  result TEXT,
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()
);
