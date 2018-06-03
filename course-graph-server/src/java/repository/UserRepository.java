package java.repository;


import java.domain.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface UserRepository extends Neo4jRepository<User, Long> {

    @Override
    Optional<User> findById(Long id);

    Optional<User> findByEmail(String username);
}
