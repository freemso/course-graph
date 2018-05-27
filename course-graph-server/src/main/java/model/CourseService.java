package main.java.model;

import neo4j.domain.CourseModule;
import neo4j.repositories.CourseModuleRepository;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    private final CourseModuleRepository courseModuleRepository;

    public CourseService(CourseModuleRepository courseModuleRepository){
        this.courseModuleRepository = courseModuleRepository;
    }

    //public String json


}
