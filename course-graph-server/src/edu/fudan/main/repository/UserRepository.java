package edu.fudan.main.repository;


import edu.fudan.main.domain.User;
import edu.fudan.main.domain.UserType;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;
import java.util.Optional;

public interface   UserRepository extends Neo4jRepository<User, Long>{
    Optional<User> findByEmail(String email);

    List<User> findByName(String name);

    //List<User> findByType(UserType type);

}
