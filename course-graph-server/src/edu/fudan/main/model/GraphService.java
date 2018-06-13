package edu.fudan.main.model;

import edu.fudan.main.exception.CourseGraphConflictException;
import edu.fudan.main.repository.CourseRepository;
import edu.fudan.main.repository.GraphRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GraphService {

    private final CourseRepository courseRepository;
    private final GraphRepository graphRepository;

    @Autowired
    public GraphService(CourseRepository courseRepository, GraphRepository graphRepository) {
        this.courseRepository = courseRepository;
        this.graphRepository = graphRepository;
    }

    /**
     * add a new graph
     * @param courseGraphName
     * @param courseGraphId
     */
    public void addNewGraph(String courseGraphName, Long courseGraphId){
        if(graphRepository.existsById(courseGraphId))
            throw new CourseGraphConflictException(courseGraphId);
        if(graphRepository.existsByName(courseGraphName))
            throw new CourseGraphConflictException(courseGraphName);
        //todo

    }

    public void updateGraph(Long courseGraphId, String jsMindData){
        //todo
    }

}
