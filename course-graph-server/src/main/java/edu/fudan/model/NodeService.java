package edu.fudan.model;

import edu.fudan.config.Constants;
import edu.fudan.domain.*;
import edu.fudan.dto.response.LectureResp;
import edu.fudan.dto.response.ResourceResp;
import edu.fudan.exception.*;
import edu.fudan.repository.GraphRepository;
import edu.fudan.repository.LectureRepository;
import edu.fudan.repository.NodeRepository;
import edu.fudan.repository.ResourceRepository;
import edu.fudan.domain.*;
import edu.fudan.exception.*;
import edu.fudan.domain.*;
import edu.fudan.exception.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class NodeService {


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
        for (Question question : questionList) {
            questionService.deleteQuestion(question);
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
        getNodesFromMindData(mindData, newNodes, graph.getCourse().getCourseId());

        // Find deleted nodes and delete them from database
        Set<String> oldIds = new HashSet<>();
        Set<String> newIds = new HashSet<>();
        for (Node node : oldNodes) {
            oldIds.add(node.getNodeId());
        }
        for (Node node : newNodes) {
            newIds.add(node.getNodeId());
        }

        oldIds.removeAll(newIds);
        for (String nodeId : oldIds) {
            deleteNode(nodeId);
        }

        graph.setNodeSet(newNodes);
        graphRepository.save(graph);
    }

    /**
     * Recursively get nodes in this mind-map
     *
     * @param currentRoot the root of the mind-map json object
     * @param newNodes    a list to save all nodes in this mind-map
     */
    private void getNodesFromMindData(JSONObject currentRoot, Set<Node> newNodes, long courseId) {
        Node node = new Node(currentRoot.get("id").toString(), currentRoot.get("topic").toString(), courseId);
        newNodes.add(node);
        if (currentRoot.has("children")) {
            JSONArray children = (JSONArray) currentRoot.get("children");
            for (int i = 0; i < children.length(); i++) {
                getNodesFromMindData((JSONObject) children.get(i), newNodes, courseId);
            }
        }
    }


    public List<ResourceResp> getAllResourcesOfNode(User currentUser, String nodeId) {
        // Node must exist
        Node node = nodeRepository.findById(nodeId).orElseThrow(
                NodeNotFoundException::new
        );

        // Current login user must have read permission to this node
        if (!permissionService.checkReadPermOfCourse(currentUser, node.getCourseId())) {
            throw new PermissionDeniedException();
        }

        List<ResourceResp> resourceRespList = new ArrayList<>();
        for (Resource r : node.getResourceList()) {
            resourceRespList.add(new ResourceResp(r));
        }
        return resourceRespList;
    }

    public List<LectureResp> getAllLecturesOfNode(User currentUser, String nodeId) {
        // Node must exist
        Node node = nodeRepository.findById(nodeId).orElseThrow(
                NodeNotFoundException::new
        );

        // Current login user must have read permission to this node
        if (!permissionService.checkReadPermOfCourse(currentUser, node.getCourseId())) {
            throw new PermissionDeniedException();
        }

        List<LectureResp> lectureRespList = new ArrayList<>();
        for (Lecture r : node.getLectureList()) {
            lectureRespList.add(new LectureResp(r));
        }
        return lectureRespList;

    }

    public ResourceResp getResourceMeta(User currentUser, long rid) {
        Resource resource = resourceRepository.findById(rid).orElseThrow(
                ResourceNotFoundExeception::new
        );

        // Current login user must have read permission to this node
        if (!permissionService.checkReadPermOfCourse(currentUser, resource.getCourseId())) {
            throw new PermissionDeniedException();
        }

        return new ResourceResp(resource);
    }

    public LectureResp getLectureMeta(User currentUser, long lid) {
        Lecture lecture = lectureRepository.findById(lid).orElseThrow(
                LectureNotFoundException::new
        );

        // Current login user must have read permission to this node
        if (!permissionService.checkReadPermOfCourse(currentUser, lecture.getCourseId())) {
            throw new PermissionDeniedException();
        }

        return new LectureResp(lecture);
    }

    public ResourceResp addFileResourcesToNode(User currentUser, String nodeId, MultipartFile file) {
        // Node must exist
        Node node = nodeRepository.findById(nodeId).orElseThrow(
                NodeNotFoundException::new
        );

        if (!permissionService.checkWritePermOfCourse(currentUser, node.getCourseId())) {
            throw new PermissionDeniedException();
        }

        // Assign an id resource
        long resourceId = RandomIdGenerator.getInstance().generateRandomLongId(resourceRepository);

        // Save the file to local file system
        // Create a local file and save the file into it
        Path filePath = Paths.get(Constants.FILE_PATH + resourceId + ".file");
        File localFile = filePath.toFile();
        try {
            file.transferTo(localFile);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResourceIOException(e.getMessage());
        }

        // Create a new resource and save it to the database
        // Title is the  original file name
        String title = file.getOriginalFilename();
        // Link is the path to a local file
        String link = localFile.getAbsolutePath();
        Resource resource = new Resource(resourceId, title, link, ResourceType.FILE, node.getCourseId());

        // Add it to the node
        node.addResource(resource);

        resourceRepository.save(resource);
        nodeRepository.save(node);

        return new ResourceResp(resource);
    }

    public ResourceResp addUrlResourceToNode(User currentUser, String nodeId, String link, String title) {
        // Get the node
        Node node = nodeRepository.findById(nodeId).orElseThrow(
                NodeNotFoundException::new
        );

        if (!permissionService.checkWritePermOfCourse(currentUser, node.getCourseId())) {
            throw new PermissionDeniedException();
        }

        // Assign an id resource
        long resourceId = RandomIdGenerator.getInstance().generateRandomLongId(resourceRepository);

        // Create the resource and add it to node
        Resource resource = new Resource(resourceId, title, link, ResourceType.URL, node.getCourseId());
        node.addResource(resource);

        resourceRepository.save(resource);
        nodeRepository.save(node);

        return new ResourceResp(resource);
    }


    public LectureResp addLectureToNode(User currentUser, String nodeId, MultipartFile file) {
        // Get the node
        Node node = nodeRepository.findById(nodeId).orElseThrow(
                NodeNotFoundException::new
        );

        if (!permissionService.checkWritePermOfCourse(currentUser, node.getCourseId())) {
            throw new PermissionDeniedException();
        }

        // Assign an id for lecture
        long lectureId = RandomIdGenerator.getInstance().generateRandomLongId(lectureRepository);

        // Create a local file and save the file into it
        Path filePath = Paths.get(Constants.LECTURE_PATH + lectureId + ".file");
        File localFile = filePath.toFile();
        try {
            file.transferTo(localFile);
        } catch (IOException e) {
            e.printStackTrace();
            throw new LectureIOException(e.getMessage());
        }

        // Create a new lecture and save it to the database
        // Title is the  original file name
        String title = file.getOriginalFilename();
        // Link is the path to a local file
        String link = localFile.getAbsolutePath();
        Lecture lecture = new Lecture(lectureId, title, link, node.getCourseId());

        node.addLecture(lecture);

        lectureRepository.save(lecture);
        nodeRepository.save(node);

        return new LectureResp(lecture);

    }

    public void deleteResource(User currentUser, long resourceId) {
        // Resource must exist
        Resource resource = resourceRepository.findById(resourceId).orElseThrow(
                ResourceNotFoundExeception::new
        );

        // Check permission
        if (!permissionService.checkWritePermOfCourse(currentUser, resource.getCourseId())) {
            throw new PermissionDeniedException();
        }

        deleteResource(resource);
    }

    private void deleteResource(Resource resource) {
        if (resource.getType() == ResourceType.FILE) {
            // If resource is file, delete the file in file system
            // Get the file by resource's link (absolute file path) and delete the file
            File file = new File(Constants.FILE_PATH + resource.getResourceId() + ".file");
            if (file.exists() && !file.delete()) {
                // File exists but deletion is NOT successful
                throw new ResourceIOException();
            }
        }
        // Delete resource from database
        resourceRepository.delete(resource);
    }

    public void deleteLecture(User currentUser, long lectureId) {
        // Lecture must exist
        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(
                LectureNotFoundException::new
        );

        // Check permission
        if (!permissionService.checkWritePermOfCourse(currentUser, lecture.getCourseId())) {
            throw new PermissionDeniedException();
        }

        deleteLecture(lecture);
    }

    private void deleteLecture(Lecture lecture) {
        // Delete file
        File file = new File(Constants.LECTURE_PATH + lecture.getLectureId() + ".file");
        if (file.exists() && !file.delete()) {
            // File exists but deletion is NOT successful
            throw new LectureIOException();
        }
        // Delete from database
        lectureRepository.delete(lecture);
    }

    public InputStreamResource downloadResourceFile(User currentUser, long resourceId) {
        // Resource must exists
        Resource resource = resourceRepository.findById(resourceId).orElseThrow(
                ResourceNotFoundExeception::new
        );

        // Check permission
        if (!permissionService.checkReadPermOfCourse(currentUser, resource.getCourseId())) {
            throw new PermissionDeniedException();
        }

        if (resource.getType() != ResourceType.FILE) {
            throw new ResourceNotFoundExeception();
        }

        //return a file stream to controller rather than all bytes of the file
        //to handle large files not only small files. when faced with file problems,
        //always steam, never keep fully in memory
        File resourceFile = new File(Constants.FILE_PATH + resourceId + ".file");

        try {
            return new InputStreamResource(new FileInputStream(resourceFile));
        } catch (FileNotFoundException e) {
            throw new ResourceIOException();
        }
    }

    public InputStreamResource downloadLecture(User currentUser, long lectureId) {
        // Lecture must exist
        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(
                ResourceNotFoundExeception::new
        );

        // Check permission
        if (!permissionService.checkReadPermOfCourse(currentUser, lecture.getCourseId())) {
            throw new PermissionDeniedException();
        }

        //return a file stream to controller rather than all bytes of the file
        //to handle large files not only small files. when faced with file problems,
        //always steam, never keep fully in memory
        File lectureFile = new File(Constants.LECTURE_PATH + lectureId + ".file");

        try {
            return new InputStreamResource(new FileInputStream(lectureFile));
        } catch (FileNotFoundException e) {
            throw new LectureIOException();
        }
    }
}
