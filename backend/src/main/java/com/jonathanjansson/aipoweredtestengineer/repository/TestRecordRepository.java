package com.jonathanjansson.aipoweredtestengineer.repository;

import com.jonathanjansson.aipoweredtestengineer.model.TestRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRecordRepository extends JpaRepository<TestRecord, Long> {
}
