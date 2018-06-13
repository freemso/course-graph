package edu.fudan.main.model;

import edu.fudan.main.domain.Course;
import edu.fudan.main.domain.Graph;
import edu.fudan.main.domain.Node;
import edu.fudan.main.domain.User;
import edu.fudan.main.dto.response.GraphMetaResp;
import edu.fudan.main.exception.CourseNotFoundException;
import edu.fudan.main.exception.GraphConflictException;
import edu.fudan.main.exception.GraphNotFoundException;
import edu.fudan.main.exception.PermissionDeniedException;
import edu.fudan.main.repository.CourseRepository;
import edu.fudan.main.repository.GraphRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class GraphService {

    private final CourseRepository courseRepository;

    private final GraphRepository graphRepository;

    private final NodeService nodeService;

    @Autowired
    public GraphService(CourseRepository courseRepository, GraphRepository graphRepository, NodeService nodeService) {
        this.courseRepository = courseRepository;
        this.graphRepository = graphRepository;
        this.nodeService = nodeService;
    }

    /**
     * Create a new graph and add it to the course
     *
     * @param currentUser, current login user
     * @param name         of the graph
     * @param courseId,    id of the course
     */
    public GraphMetaResp createNewGraph(User currentUser, String name, long courseId) {
        // Course must first exist
        Course course = courseRepository.findById(courseId).orElseThrow(
                () -> new CourseNotFoundException(courseId)
        );

        // Current user must be the teacher of the course
        if (!course.getTeacher().equals(currentUser)) {
            throw new PermissionDeniedException();
        }

        // New graph must have a unique name
        if (graphRepository.existsByName(name, courseId)) {
            throw new GraphConflictException(name);
        }

        // Generate a new id for the graph
        long newGraphId = RandomIdGenerator.getInstance().generateRandomLongId(graphRepository);

        // Save it to database
        Graph graph = graphRepository.save(new Graph(newGraphId, name, course));

        return new GraphMetaResp(graph);
    }

    /**
     * Delete the graph.
     * For controller.
     * Need to check login user ownership.
     * @param currentUser, current login user
     * @param graphId, id of the graph
     */
    public void deleteGraph(User currentUser, long graphId) {
        // First check if the graph exists
        Graph graph = graphRepository.findById(graphId).orElseThrow(
                GraphNotFoundException::new
        );

        // Check if the current login user is the teacher/owner of the graph
        // TODO maybe need to set depth
        if (!graph.getCourse().getTeacher().equals(currentUser)) {
            throw new PermissionDeniedException();
        }

        // Delete all nodes in the graph
        for(Node node : graph.getNodeSet()) {
            nodeService.deleteNode(node.getNodeId());
        }

        // Delete the graph itself
        graphRepository.deleteById(graphId);
    }

    /**
     * Delete the graph.
     * For course service.
     * Do NOT need to check login user.
     * @param graphId, id of the graph
     */
    public void deleteGraph(long graphId) {
        // First check if the graph exists
        Graph graph = graphRepository.findById(graphId).orElseThrow(
                GraphNotFoundException::new
        );

        // Delete all nodes in the graph
        for (Node node : graph.getNodeSet()) {
            nodeService.deleteNode(node.getNodeId());
        }
    }

//    /**
//     * update course graph by updating its jsMindData
//     *
//     * @param courseGraphId id of the course graph to be updated
//     * @param jsMindData    mind map json string
//     */
//    public GraphMetaResp updateGraph(Long courseGraphId, String jsMindData) {
//        Graph graph = graphRepository.findById(courseGraphId).orElseThrow(
//                GraphNotFoundException::new
//        );
//
//        //update jsmind json string
//        graph.setJsMindData(jsMindData);
//        graphRepository.save(graph);
//
//        //update nodes according new mind map
//        nodeService.updateNodes(courseGraphId, jsMindData);
//        return new GraphMetaResp(graph);
//    }


    /**
     * get all graphs' metadata of one course
     * @param courseId
     * @return list of graph metadata
     */
    public List<GraphMetaResp> getAllGraphsOfCourse(long courseId) {
        List<Graph> graphs = courseRepository.findById(courseId).orElseThrow(
                CourseNotFoundException::new
        ).getGraphList();

        List<GraphMetaResp> metaRespList = new ArrayList<>();
        for (Graph graph : graphs) {
            metaRespList.add(new GraphMetaResp(graph));
        }
        return metaRespList;
    }

    /**
     * get graph's mind map
     * @param courseGraphId
     * @return jsmind json string
     */
    public String getJsMindData(long courseGraphId) {
        String jsMindData = graphRepository.findById(courseGraphId).orElseThrow(
                GraphNotFoundException::new
        ).getJsMindData();
        return jsMindData;
    }


}
