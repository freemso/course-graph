package edu.fudan.main.model;

import edu.fudan.main.domain.CourseGraph;
import edu.fudan.main.dto.response.CourseGraphMetaResp;
import edu.fudan.main.exception.CourseGraphConflictException;
import edu.fudan.main.exception.CourseNotFoundException;
import edu.fudan.main.repository.CourseRepository;
import edu.fudan.main.repository.GraphRepository;
import edu.fudan.main.util.RandomIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
     * add a new graph to the course
     * @param courseGraphName
     * @param courseId
     */
    public CourseGraphMetaResp addNewGraph(String courseGraphName, long courseId){
        if(!courseRepository.existsById(courseId))
            throw new CourseNotFoundException(courseId);
        if(graphRepository.existsByCourseName(courseGraphName, courseId))
            throw new CourseGraphConflictException(courseGraphName);
        long courseGraphId = RandomIdGenerator.getInstance().generateRandomLongId(graphRepository);
        CourseGraph courseGraph = new CourseGraph(courseGraphId, courseGraphName);
        graphRepository.save(courseGraph, 0);
        return new CourseGraphMetaResp(courseGraphId, courseId, courseGraphName);
        //todo
    }

    /**
     * update course graph by updating its jsMindData
     * @param courseGraphId id of the course graph to be updated
     * @param jsMindData mind map json string
     */
    public void updateGraph(Long courseGraphId, String jsMindData){
        //todo
    }

    public List<CourseGraphMetaResp> listAllGraphs(long courseId){
        //todo
        return null;
    }

    public String getJsMindData(long courseGraphId){
        //todo
        return null;
    }

    public void deleteGraph(long courseGraphId){
        //todo
    }




}
