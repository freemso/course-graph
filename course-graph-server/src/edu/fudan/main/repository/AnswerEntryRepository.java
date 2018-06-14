package edu.fudan.main.repository;

import edu.fudan.main.domain.AnswerEntry;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface AnswerEntryRepository extends Neo4jRepository<AnswerEntry, Long>{
}
