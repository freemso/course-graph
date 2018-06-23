package edu.fudan.repository;

import edu.fudan.domain.Resource;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ResourceRepository extends Neo4jRepository<Resource, Long> {
}
