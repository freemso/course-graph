package edu.fudan.main.repository;

import edu.fudan.main.domain.Resource;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ResourceRepository extends Neo4jRepository<Resource, Long> {
}
