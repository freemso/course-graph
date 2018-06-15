package edu.fudan.main.repository;

import edu.fudan.main.domain.Node;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface NodeRepository extends Neo4jRepository<Node, String>{

}
