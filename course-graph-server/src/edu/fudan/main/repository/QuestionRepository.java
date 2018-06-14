package edu.fudan.main.repository;

import edu.fudan.main.domain.Question;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface QuestionRepository extends Neo4jRepository<Question, Long>{
}
