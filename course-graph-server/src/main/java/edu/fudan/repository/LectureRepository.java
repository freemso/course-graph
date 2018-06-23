package edu.fudan.repository;

import edu.fudan.domain.Lecture;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface LectureRepository extends Neo4jRepository<Lecture, Long> {
}
