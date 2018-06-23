package edu.fudan.repository;

import edu.fudan.domain.QuestionMultipleChoice;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface QuestionMultipleChoiceRepository extends Neo4jRepository<QuestionMultipleChoice, Long>{
}
