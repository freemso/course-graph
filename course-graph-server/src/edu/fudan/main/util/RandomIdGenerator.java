package edu.fudan.main.util;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import static edu.fudan.main.config.Constants.ID_LENGTH;

public class RandomIdGenerator {

    private static final RandomIdGenerator instance = new RandomIdGenerator();

    private RandomIdGenerator(){}

    public static RandomIdGenerator getInstance() {
        return instance;
    }

    public long generateRandomLongId() {
        long lower = (long) Math.pow(10, ID_LENGTH);
        long upper = (long) Math.pow(10, ID_LENGTH + 1);
        return lower + (long) (Math.random() * (upper - lower));
    }

    public long generateRandomLongId(Neo4jRepository repository){
        long id = 0;
        do{
            id = generateRandomLongId();
        }while(repository.existsById(id));
        return id;
    }
}
