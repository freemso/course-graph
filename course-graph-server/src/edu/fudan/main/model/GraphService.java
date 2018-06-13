package edu.fudan.main.model;

import edu.fudan.main.domain.Course;
import edu.fudan.main.domain.Graph;
import edu.fudan.main.domain.User;
import edu.fudan.main.dto.response.GraphMetaResp;
import edu.fudan.main.exception.CourseNotFoundException;
import edu.fudan.main.exception.GraphConflictException;
import edu.fudan.main.exception.PermissionDeniedException;
import edu.fudan.main.repository.CourseRepository;
import edu.fudan.main.repository.GraphRepository;
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
     * Create a new graph and add it to the course
     * @param currentUser, current login user
     * @param name of the graph
     * @param courseId, id of the course
     */
    public GraphMetaResp createNewGraph(User currentUser, String name, long courseId) {
        // Course must first exist
        Course course = courseRepository.findById(courseId).orElseThrow(
                () -> new CourseNotFoundException(courseId)
        );

        // Current user must be the teacher of the course
        if(!course.getTeacher().equals(currentUser)) {
            throw new PermissionDeniedException();
        }

        // New graph must have a unique name
        if(graphRepository.existsByName(name, courseId)) {
            throw new GraphConflictException(name);
        }

        // Generate a new id for the graph
        long newGraphId = RandomIdGenerator.getInstance().generateRandomLongId(graphRepository);

        // Save it to database
        Graph graph = graphRepository.save(new Graph(newGraphId, name, course));

        return new GraphMetaResp(graph);
    }


    public void deleteGraph(long courseGraphId){
        //todo
    }


    /**
     * update course graph by updating its jsMindData
     * @param courseGraphId id of the course graph to be updated
     * @param jsMindData mind map json string
     */
    public void updateGraph(Long courseGraphId, String jsMindData){
        //todo
//        Optional<CourseGraph> courseGraph = graphRepository.findById(courseGraphId);
//        if(!courseGraph.isPresent())
//            throw new GraphNotFoundException(courseGraphId);
//        CourseGraph courseGraph1 = courseGraph.get();
//        courseGraph1.setJsMindData(jsMindData);
//        graphRepository.save(courseGraph1, 0);
    }

    public List<GraphMetaResp> listAllGraphs(long courseId){
        //todo
        return null;
    }

    public String getJsMindData(long courseGraphId){
        // TODO
//        Optional<CourseGraph> courseGraph = graphRepository.findById(courseGraphId);
//        if(!courseGraph.isPresent())
//            throw new GraphNotFoundException(courseGraphId);
//        return courseGraph.get().getJsMindData();
        return null;
    }




}
