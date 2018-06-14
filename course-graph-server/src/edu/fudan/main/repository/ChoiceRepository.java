package edu.fudan.main.repository;

import edu.fudan.main.domain.Choice;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ChoiceRepository extends Neo4jRepository<Choice, Long>{
}
