package edu.fudan.main.model;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Service;

import static edu.fudan.main.config.Constants.ID_LENGTH;

@Service
class RandomIdGenerator {

    private static final RandomIdGenerator instance = new RandomIdGenerator();

    private RandomIdGenerator(){}

    static RandomIdGenerator getInstance() {
        return instance;
    }

    long generateRandomLongId() {
        long lower = (long) Math.pow(10, ID_LENGTH);
        long upper = (long) Math.pow(10, ID_LENGTH + 1);
        return lower + (long) (Math.random() * (upper - lower));
    }

    long generateRandomLongId(Neo4jRepository repository){
        long id;
        do {
            id = generateRandomLongId();
        } while(repository.existsById(id));
        return id;
    }
}
