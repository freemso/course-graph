package edu.fudan.repository;

import edu.fudan.domain.Node;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface NodeRepository extends Neo4jRepository<Node, String>{

}
