package edu.fudan.main.repository;


import edu.fudan.main.domain.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface UserRepository extends Neo4jRepository<User, Long> {

    Optional<User> findByEmail(String email);

    /**
     * Set the name of the user with id equals to {id} to {newName}
     * @return the updated version of the user
     */
    Optional<User> updateNameOfUserWithId(Long id, String newName);

    Optional<User> updatePasswordOfUserWithId(Long id, String newPassword);

    Optional<User> updateEmailOfUserWithId(Long id, String newEmail);

}
