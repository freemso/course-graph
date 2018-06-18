package edu.fudan.main.model;

import edu.fudan.main.domain.*;
import edu.fudan.main.dto.response.LectureResp;
import edu.fudan.main.dto.response.ResourceResp;
import edu.fudan.main.exception.*;
import edu.fudan.main.repository.*;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class NodeService {

    private static final String RESOURCE_PATH = "files/resources";

    private static final String LECTURE_PATH = "files/lecture";

    private final NodeRepository nodeRepository;

    private final GraphRepository graphRepository;

    private final ResourceRepository resourceRepository;

    private final LectureRepository lectureRepository;

    private final PermissionService permissionService;

    private final QuestionService questionService;


    @Autowired
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
            this.deleteLecture(lecture.getLectureId());
        }

        // Remove relations
//        node.removeRelations();
//        nodeRepository.save(node);

        // Delete the course node itself
        nodeRepository.delete(node);
    }

    /**
     * Update all nodes related to one graph according to the given jsmind data
     *
     * @param graphId    id of the graph
     * @param jsMindData new jsmind data
     */
    public void updateNodes(long graphId, String jsMindData) {
        // Parse json string to json object
        JSONObject mindObject;
        try {
            mindObject = new JSONObject(jsMindData);
        } catch (JSONException e) {
            throw new InvalidJsmindException();
        }
        // Get `data` field
        JSONObject mindData = (JSONObject) mindObject.get("data");

        // Get existing nodes related to the graph
        Graph graph = graphRepository.findById(graphId).orElseThrow(
                GraphNotFoundException::new
        );
        Set<Node> oldNodes = graph.getNodeSet();

        // Update old nodes and create new nodes
        Set<Node> newNodes = new HashSet<>();
        getNodesFromMindData(mindData, newNodes);

        // Find deleted nodes and delete them from database
        Set<String> oldIds = new HashSet<>();
        Set<String> newIds = new HashSet<>();
        for(Node node : oldNodes) {
            oldIds.add(node.getNodeId());
        }
        for(Node node : newNodes) {
            newIds.add(node.getNodeId());
        }

        oldIds.removeAll(newIds);
        for(String nodeId : oldIds) {
            deleteNode(nodeId);
        }

        graph.setNodeSet(newNodes);
        graphRepository.save(graph);
    }

    /**
     * Recursively get nodes in this mind-map
     *
     * @param currentRoot the root of the mind-map json object
     * @param newNodes a list to save all nodes in this mind-map
     */
    private void getNodesFromMindData(JSONObject currentRoot, Set<Node> newNodes) {
        Node node = new Node((String) currentRoot.get("id"), (String) currentRoot.get("topic"));
        newNodes.add(node);
        if (currentRoot.has("children")) {
            JSONArray children = (JSONArray)currentRoot.get("children");
            for (int i = 0; i < children.length(); i++) {
                getNodesFromMindData((JSONObject) children.get(i), newNodes);
            }
        }
    }


    public List<ResourceResp> getAllResourcesOfNode(User currentUser, String nodeId){

        Node node = nodeRepository.findById(nodeId).orElseThrow(
                NodeNotFoundException::new
        );
        List<ResourceResp> resourceResps = new ArrayList<>();
        for(Resource r: node.getResourceList()){
            resourceResps.add(new ResourceResp(r));
        }
        return resourceResps;
    }

    public ResourceResp addNewResourceToNode(User currentUser, String nodeId, String title, String link, byte[] file){
        //get the node
        Node node = nodeRepository.findById(nodeId).orElseThrow(
                NodeNotFoundException::new
        );

        /*check the permission of current user,
         *since it needs to set the depth to 2 to get the course, so simply check the user type
        */
        if(!currentUser.getType().equals(UserType.TEACHER))
            throw new PermissionDeniedException();

        //for link
        if(link != null){
            Resource resource = new Resource(RandomIdGenerator.getInstance().generateRandomLongId(resourceRepository),
                    title, link, node, ResourceType.URL);
            resourceRepository.save(resource);
            return new ResourceResp(resource);

        }else if(file != null){
            String path = RESOURCE_PATH + title;
            //save it to local directory
            File localFile = new File(path);
            try {
                FileUtils.writeByteArrayToFile(localFile, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //for file resource, link means the absolute path of this file on the server.
            Resource resource = new Resource(RandomIdGenerator.getInstance().generateRandomLongId(resourceRepository),
                    title, localFile.getAbsolutePath(), node, ResourceType.FILE);
            return new ResourceResp(resource);
        }
        return null;
    }

    public void deleteResource(User currentUser, long resourceId){
        //get the resource
        Resource resource = resourceRepository.findById(resourceId).orElseThrow(
                ResourceNotFoundExeception::new
        );

        //check permission
        if(currentUser.getType().equals(UserType.STUDENT))
            throw new PermissionDeniedException();

        deleteResource(resource);

    }

    private void deleteResource(Resource resource) {
        //if resource's type is url, just delete it from database
        if(resource.getTitle().equals(ResourceType.URL)){
            resourceRepository.delete(resource);
        }else{
            //get the file by resource's link (absolute file path) and delete the file
            File file = new File(resource.getLink());
            if(file.exists())
                file.delete();

            //delete resource from database
            resourceRepository.delete(resource);
        }
    }

    public ResourceResp getResourceMeta(long resourceId) {
        Resource resource = resourceRepository.findById(resourceId).orElseThrow(
                ResourceNotFoundExeception::new
        );

        return new ResourceResp(resource);
    }


    public FileInputStream downloadFileOfNode(long resourceId) throws FileNotFoundException {
        //check resource type
        Resource resource = resourceRepository.findById(resourceId).orElseThrow(
                ResourceNotFoundExeception::new
        );

        if(resource.getType().equals(ResourceType.URL))
            throw new ResourceNotFoundExeception();

        //return a file stream to controller rather than all bytes of the file
        //to handle large files not only small files. when faced with file problems,
        //always steam, never keep fully in memory
        return new FileInputStream(new File(resource.getLink()));
    }


    public LectureResp addNewLectureToNode(User currentUser, String nodeId){
        //TODO
        return null;
    }

    public FileInputStream downloadLecture(long lectureId){
        //TODO

        return null;
    }

    public List<LectureResp> getAllLecturesOfNode(User currentUser, String nodeId) {
        // TODO
        return null;
    }





    public void deleteLecture(User currentUser, long lectureId){
        //check user permission
        if(currentUser.getType().equals(UserType.STUDENT))
            throw new PermissionDeniedException();

        deleteLecture(lectureId);
    }

    private void deleteLecture(long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(
                LectureNotFoundException::new
        );

        //delete file
        File file = new File(lecture.getLink());
        if(file.exists())
            file.delete();

        //delete from database
        lectureRepository.delete(lecture);
    }



}
