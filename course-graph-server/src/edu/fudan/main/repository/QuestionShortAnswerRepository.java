package edu.fudan.main.repository;

import edu.fudan.main.domain.QuestionShortAnswer;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface QuestionShortAnswerRepository extends Neo4jRepository<QuestionShortAnswer, Long>{

}
