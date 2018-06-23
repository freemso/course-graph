package edu.fudan.repository;

import edu.fudan.domain.AnswerEntry;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface AnswerEntryRepository extends Neo4jRepository<AnswerEntry, Long>{
}
