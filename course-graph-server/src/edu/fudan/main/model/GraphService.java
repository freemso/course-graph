package edu.fudan.main.model;

import edu.fudan.main.domain.CourseGraph;
import edu.fudan.main.domain.CourseGraphMeta;
import edu.fudan.main.exception.CourseGraphConflictException;
import edu.fudan.main.exception.CourseGraphNotFoundException;
import edu.fudan.main.exception.CourseNotFoundException;
import edu.fudan.main.repository.CourseRepository;
import edu.fudan.main.repository.GraphRepository;
import edu.fudan.main.util.RandomIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

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
    public CourseGraphMeta addNewGraph(String courseGraphName, long courseId){
        if(!courseRepository.existsById(courseId))
            throw new CourseNotFoundException(courseId);
        if(graphRepository.existsByCourseName(courseGraphName, courseId))
            throw new CourseGraphConflictException(courseGraphName);
        long courseGraphId = RandomIdGenerator.getInstance().generateRandomLongId(graphRepository);
        CourseGraph courseGraph = new CourseGraph(courseGraphId, courseGraphName);
        graphRepository.save(courseGraph, 0);
        return new CourseGraphMeta(courseGraphId, courseId, courseGraphName);
        //todo
    }

    /**
     * update course graph by updating its jsMindData
     * @param courseGraphId id of the course graph to be updated
     * @param jsMindData mind map json string
     */
    public void updateGraph(Long courseGraphId, String jsMindData){
        //todo
        Optional<CourseGraph> courseGraph = graphRepository.findById(courseGraphId);
        if(!courseGraph.isPresent())
            throw new CourseGraphNotFoundException(courseGraphId);
        CourseGraph courseGraph1 = courseGraph.get();
        courseGraph1.setJsMindData(jsMindData);
        graphRepository.save(courseGraph1, 0);
    }

    public List<CourseGraphMeta> listAllGraphs(long courseId){
        return graphRepository.listAllGraphs(courseId);
    }

    public String getJsMindData(long courseGraphId){
        Optional<CourseGraph> courseGraph = graphRepository.findById(courseGraphId);
        if(!courseGraph.isPresent())
            throw new CourseGraphNotFoundException(courseGraphId);
        return courseGraph.get().getJsMindData();
    }

    public void deleteGraph(long courseGraphId){
        //todo
    }




}
