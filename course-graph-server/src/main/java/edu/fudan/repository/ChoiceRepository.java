package edu.fudan.repository;

import edu.fudan.domain.Choice;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ChoiceRepository extends Neo4jRepository<Choice, Long>{
}
