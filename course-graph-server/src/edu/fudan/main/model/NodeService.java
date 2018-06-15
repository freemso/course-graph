package edu.fudan.main.model;

import edu.fudan.main.domain.*;
import edu.fudan.main.dto.response.LectureResp;
import edu.fudan.main.dto.response.ResourceResp;
import edu.fudan.main.exception.*;
import edu.fudan.main.repository.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class NodeService {

    private final NodeRepository nodeRepository;

    private final GraphRepository graphRepository;

    private final ResourceRepository resourceRepository;

    private final LectureRepository lectureRepository;

    private final PermissionService permissionService;

    private final QuestionService questionService;

    public NodeService(NodeRepository nodeRepository,
                       GraphRepository graphRepository,
                       ResourceRepository resourceRepository,
                       LectureRepository lectureRepository,
                       PermissionService permissionService,
                       QuestionService questionService) {
        this.nodeRepository = nodeRepository;
        this.graphRepository = graphRepository;
        this.resourceRepository = resourceRepository;
        this.lectureRepository = lectureRepository;
        this.permissionService = permissionService;
        this.questionService = questionService;
    }

    public void deleteNode(String nodeId) {
        // Node must exist
        Node node = nodeRepository.findById(nodeId).orElseThrow(
                NodeNotFoundException::new
        );

        // Get all lists that the course node owns
        List<Question> questionList = node.getQuestionList();
        List<Resource> resourceList = node.getResourceList();
        List<Lecture> lectureList = node.getLectureList();

        // Delete questions
        for(Question question : questionList) {
            questionService.deleteQuestion(question.getQuestionId());
        }
        // Delete resources
        for (Resource resource : resourceList) {
            this.deleteResource(resource);
        }
        // Delete lectures
        for (Lecture lecture : lectureList) {
            this.deleteLecture(lecture);
        }

        // Remove relations
        node.removeRelations();
        nodeRepository.save(node);

        // Delete the course node itself
        nodeRepository.delete(node);
    }

    /**
     * update all nodes related to one graph according to new jsmind data
     *
     * @param graphId    id of the graph
     * @param jsMindData new jsmind data
     */
    public void updateNodes(long graphId, String jsMindData) {
        //parse json string to json object
        JSONObject mindObject;
        try {
            mindObject = new JSONObject(jsMindData);
        } catch (JSONException e) {
            throw new InvalidJsmindException();
        }
        JSONObject mindData = (JSONObject) mindObject.get("data");

        //get existing nodes related to the graph
        Graph graph = graphRepository.findById(graphId).orElseThrow(
                GraphNotFoundException::new
        );
        Set<Node> oldNodes = graph.getNodeSet();

        //update old nodes and create new nodes
        Set<Node> newNodes = new HashSet<>();
        getNodesFromMindData(mindData, newNodes);

//        //save() will merge new node with old node if it existed before, otherwise add it to the database
//        nodeRepository.saveAll(newNodes);
//        graph.setNodeSet(newNodes);
//        graphRepository.save(graph);

        //find deleted nodes and delete them from database
        Set<String> oldIds = new HashSet<>(), newIds = new HashSet<>();
        for(Node node: oldNodes)
            oldIds.add(node.getNodeId());
        for(Node node: newNodes)
            newIds.add(node.getNodeId());
        oldIds.removeAll(newIds);
        for(String nodeId: oldIds)
            deleteNode(nodeId);

        //save() will merge new node with old node if it existed before, otherwise add it to the database
        graph.setNodeSet(newNodes);
        graphRepository.save(graph);
    }

    /**
     * get all nodes in this mind-map
     *
     * @param mindNode the root of the mind-map json object
     * @param newNodes a list to save all nodes in this mind-map
     */
    private void getNodesFromMindData(JSONObject mindNode, Set<Node> newNodes) {
        Node node = new Node((String) mindNode.get("id"), (String) mindNode.get("topic"));
        newNodes.add(node);
        if (mindNode.has("children")) {
            JSONArray children = (JSONArray)mindNode.get("children");
            for(int i = 0; i < children.length(); i++){
                getNodesFromMindData((JSONObject) children.get(i), newNodes);
            }
        }
    }



    public void addResource() {

    }

    public void addLecture() {

    }

    public List<ResourceResp> getAllResourcesOfNode(User currentUser, String nodeId) {
        // TODO
        return null;
    }

    public List<LectureResp> getAllLecturesOfNode(User currentUser, String nodeId) {
        // TODO
        return null;
    }

    public void deleteResource() {

    }

    private void deleteResource(Resource resource) {
        resource.removeRelation();
        resourceRepository.save(resource);

        resourceRepository.delete(resource);
    }

    public void deleteLecture() {

    }

    private void deleteLecture(Lecture lecture) {
        lecture.removeRelation();
        lectureRepository.save(lecture);

        lectureRepository.delete(lecture);
    }


}
