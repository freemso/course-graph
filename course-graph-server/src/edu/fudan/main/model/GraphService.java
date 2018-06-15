package edu.fudan.main.model;

import edu.fudan.main.domain.Course;
import edu.fudan.main.domain.Graph;
import edu.fudan.main.domain.Node;
import edu.fudan.main.domain.User;
import edu.fudan.main.dto.response.GraphMetaResp;
import edu.fudan.main.dto.response.JsmindResp;
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

    private final PermissionService permissionService;

    @Autowired
    public GraphService(CourseRepository courseRepository, GraphRepository graphRepository,
                        NodeService nodeService, PermissionService permissionService) {
        this.courseRepository = courseRepository;
        this.graphRepository = graphRepository;
        this.nodeService = nodeService;
        this.permissionService = permissionService;
    }

    /**
     * Create a new graph and add it to the course
     *
     * @param currentUser, current login user
     * @param name         of the graph
     * @param description of the graph
     * @param jsmind of the graph
     * @param courseId,    id of the course
     */
    public GraphMetaResp createNewGraph(User currentUser, String name, String description,
                                        String jsmind, long courseId) {
        // Course must first exist
        Course course = courseRepository.findById(courseId).orElseThrow(
                () -> new CourseNotFoundException(courseId)
        );

        // Current user must be the teacher of the course
        if (!permissionService.checkWritePermOfCourse(currentUser, courseId)) {
            throw new PermissionDeniedException();
        }

        // New graph must have a unique name
        if (graphRepository.existsByNameInCourse(name, courseId)) {
            throw new GraphConflictException(name);
        }

        // Generate a new id for the graph
        long newGraphId = RandomIdGenerator.getInstance().generateRandomLongId(graphRepository);


        // Save it to database
        Graph graph = graphRepository.save(new Graph(newGraphId, name, description, jsmind, course));

        if (jsmind != null && !jsmind.equals("")) {
            // Update nodes according new mind map
            nodeService.updateNodes(newGraphId, jsmind);
        }

        return new GraphMetaResp(graph);
    }

    private void deleteGraph(Graph graph) {
        // Delete all nodes in the graph
        for(Node node : graph.getNodeSet()) {
            nodeService.deleteNode(node.getNodeId());
        }

        // Remove course relationships
        graph.removeCourse();
        graphRepository.save(graph);

        // Delete the graph itself
        graphRepository.delete(graph);
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

        // Check write permission
        if (!permissionService.checkWritePermOfCourse(currentUser, graph.getCourse().getCourseId())) {
            throw new PermissionDeniedException();
        }

        deleteGraph(graph);
    }

    /**
     * Delete the graph.
     * For course service.
     * Do NOT need to get permission.
     * @param graphId, id of the graph
     */
    public void deleteGraph(long graphId) {
        // First check if the graph exists
        Graph graph = graphRepository.findById(graphId).orElseThrow(
                GraphNotFoundException::new
        );

        deleteGraph(graph);
    }

    /**
     * update course graph by updating its jsMindData
     * @param currentUser, current login user
     * @param graphId id of the course graph to be updated
     * @param jsMindData    mind map json string
     */
    public JsmindResp updateJsmind(User currentUser, long graphId, String jsMindData) {
        // Graph exists
        Graph graph = graphRepository.findById(graphId).orElseThrow(
                GraphNotFoundException::new
        );

        // Check write permission
        if (!permissionService.checkWritePermOfCourse(currentUser, graph.getCourse().getCourseId())) {
            throw new PermissionDeniedException();
        }

        // Update jsmind json string
        graph.setJsMindData(jsMindData);
        graphRepository.save(graph);

        // Update nodes according new mind map
        nodeService.updateNodes(graphId, jsMindData);
        return new JsmindResp(jsMindData);
    }


    public GraphMetaResp updateGraphMeta(User currentUser, long graphId, String name, String description) {
        // Graph exists
        Graph graph = graphRepository.findById(graphId).orElseThrow(
                GraphNotFoundException::new
        );

        // Check write permission
        if (!permissionService.checkWritePermOfCourse(currentUser, graph.getCourse().getCourseId())) {
            throw new PermissionDeniedException();
        }

        if (name != null) {
            // New name must not duplicate with other graphs of the course
            if (graphRepository.existsByNameInCourse(name, graph.getCourse().getCourseId())) {
                throw new GraphConflictException(name);
            }
            graph.setName(name);
        }

        if (description != null) {
            graph.setDescription(description);
        }

        // Save the changes and return
        return new GraphMetaResp(graphRepository.save(graph));
    }

    /**
     * Get all graph metadata of a course
     * @param courseId, id of the course
     * @return list of graph metadata
     */
    public List<GraphMetaResp> getAllGraphsOfCourse(User currentUser, long courseId) {
        // Course exists
        List<Graph> graphs = courseRepository.findById(courseId).orElseThrow(
                CourseNotFoundException::new
        ).getGraphList();

        // Current login user must have read permission
        if (!permissionService.checkReadPermOfCourse(currentUser, courseId)) {
            throw new PermissionDeniedException();
        }

        List<GraphMetaResp> metaRespList = new ArrayList<>();
        for (Graph graph : graphs) {
            metaRespList.add(new GraphMetaResp(graph));
        }
        return metaRespList;
    }

    /**
     * Get graph jsmind data
     * @param graphId, id of the graph
     * @return jsmind json string
     */
    public JsmindResp getJsmind(long graphId) {
        String jsMindData = graphRepository.findById(graphId).orElseThrow(
                GraphNotFoundException::new
        ).getJsMindData();
        return new JsmindResp(jsMindData);
    }

    /**
     * Get meta data of a graph
     * @param graphId, id of the graph
     * @return graph meta data
     */
    public GraphMetaResp getGraphMeta(long graphId) {
        Graph graph = graphRepository.findById(graphId).orElseThrow(
                GraphNotFoundException::new
        );
        return new GraphMetaResp(graph);
    }


}
