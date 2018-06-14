package edu.fudan.main.service;


import edu.fudan.main.domain.*;
import edu.fudan.main.dto.response.GraphMetaResp;
import edu.fudan.main.exception.CourseNotFoundException;
import edu.fudan.main.exception.GraphNotFoundException;
import edu.fudan.main.exception.PermissionDeniedException;
import edu.fudan.main.model.GraphService;
import edu.fudan.main.model.NodeService;
import edu.fudan.main.repository.CourseRepository;
import edu.fudan.main.repository.GraphRepository;
import edu.fudan.main.repository.NodeRepository;
import edu.fudan.main.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class GraphServiceTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GraphRepository graphRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private GraphService graphService;
    @Autowired
    private NodeRepository nodeRepository;
    @Autowired
    private NodeService nodeService;



    @Before
    public void before() throws Exception {
        Student student = new Student(0, "student", "123", "1@1.com");
        Teacher teacher = new Teacher(1, "teacher1", "123", "2@2.com");
        Teacher teacher1 = new Teacher(5, "teacher2", "123", "3@3.com");
        Course course = new Course("SOFT13", "web", 2l,  teacher);
        student.addCourse(course);
        teacher.addCourse(course);
        userRepository.save(student);
        userRepository.save(teacher);
        userRepository.save(teacher1);
        courseRepository.save(course);
    }

    @After
    public void after() throws Exception {
    }

    /**
     *
     * Method: createNewGraph(User currentUser, String name, long courseId)
     *
     */
    @Test
    public void testCreateNewGraph() throws Exception {
        //test for student
        User user = userRepository.findById(0l).get();
        try{
            GraphMetaResp graphMetaResp = graphService.createNewGraph(
                    user, "test", "some description", "", 2);
            assert false;
        }catch(PermissionDeniedException e){
            assert true;
        }

        //test for teacher
        user = userRepository.findById(1l).get();

        //when the course doesn't exist, throw exception
        try{
            graphService.createNewGraph(
                    user, "test", "some description", "", 3);
            assert false;
        }catch (CourseNotFoundException e){
            assert true;
        }

        //test for successful case
        GraphMetaResp graphMetaResp = graphService.createNewGraph(
                user, "test", "some description", "", 2);
        assertEquals(graphMetaResp.getName(), "test");

    }


    /**
     *
     * Method: updateGraph(Long courseGraphId, String jsMindData)
     *
     */
    @Test
    public void testUpdateGraph() throws Exception{

        //get the graph id
        User user = userRepository.findById(1l).get();
        GraphMetaResp graphMetaResp = graphService.createNewGraph(
                user, "test", "some description", "", 2);
        long graphId = graphMetaResp.getId();


        //update the graph by setting jsmind to jsmindBase which has 7 node
        graphService.updateGraph(graphId, jsmindBase);

        //get new nodes and jsMindData
        Set<Node> baseNodes = graphRepository.findById(graphId).get().getNodeSet();
        String jsMindData = graphRepository.findById(graphId).get().getJsMindData();

        //check jsMindData
        assertEquals(jsMindData, jsmindBase);
        //check new nodes
        assertTrue(baseNodes.size() == 7);

        //update the graph by setting jsmind to jsmindDel which delete 3 node on the base of jsmindBase
        graphService.updateGraph(graphId, jsmindDel);
        Set<Node> delNodes = graphRepository.findById(graphId).get().getNodeSet();
        jsMindData = graphRepository.findById(graphId).get().getJsMindData();

        //check jsMindData
        assertEquals(jsMindData, jsmindDel);
        //check new nodes
        assertTrue(delNodes.size() == 4);
        //check intersection of two sets
        Set<Node> intersection = (new HashSet<>(baseNodes));
        intersection.retainAll(delNodes);
        assertTrue(intersection.size() == 4);

        //update the graph by setting jsmind to jsmindAdd which add 3 node on the base of jsmindBase
        graphService.updateGraph(graphId, jsmindAdd);
        Set<Node> addNodes = graphRepository.findById(graphId).get().getNodeSet();
        jsMindData = graphRepository.findById(graphId).get().getJsMindData();

        //check jsMindData and new nodes
        assertEquals(jsMindData, jsmindAdd);
        assertTrue(addNodes.size() == 10);
        //check intersection
        intersection = new HashSet<>(addNodes);
        intersection.retainAll(baseNodes);
        assertTrue(intersection.size() == 7);

        //update the graph by setting jsmind to jsmindMod which modified 2 node on the base of jsmindBase
        graphService.updateGraph(graphId, jsMindMod);
        Set<Node> modNodes = graphRepository.findById(graphId).get().getNodeSet();
        jsMindData = graphRepository.findById(graphId).get().getJsMindData();

        //check jsMindData and new nodes
        assertEquals(jsMindData, jsMindMod);
        assertTrue(modNodes.size() == 7);
        //check intersection
        intersection = new HashSet<>(modNodes);
        intersection.retainAll(baseNodes);
        assertTrue(intersection.size() == 5);


    }



    /**
     *
     * Method: deleteGraph(User currentUser, long graphId)
     *
     */
    @Test
    public void testDeleteGraphForCurrentUserGraphId() throws Exception {
//TODO: Test goes here...
        User user1 = userRepository.findById(1l).get();
        User user2 = userRepository.findById(5l).get();
        GraphMetaResp graphMetaResp = graphService.createNewGraph(user1, "test", "test map", jsmindBase, 2);
        long graphId = graphMetaResp.getId();
        graphService.updateGraph(graphId, jsmindBase);

        //if graph doesn't exist, throw exception
        try{
            graphService.deleteGraph(user1, -1);
            assert false;
        }catch(GraphNotFoundException e){
            assert true;
        }

        //if the current login user isn't the teacher/owner of the graph, throw exception
        try {
           graphService.deleteGraph(user2, graphId);
           assert false;
        }catch(PermissionDeniedException e){
            assert true;
        }

        //before delete
        Graph graph = graphRepository.findById(graphId).get();
        assertNotNull(graph);
        Set<Node> nodes = graph.getNodeSet();
        assertTrue(nodes.size() == 7);

        graphService.deleteGraph(user1, graphId);

        //after delete, all nodes related to the graph including the graph itself will disappear
        assertFalse(graphRepository.findById(graphId).isPresent());
        for(Node node: nodes){
            assertFalse(nodeRepository.findById(node.getNodeId()).isPresent());
        }
    }

    /**
     *
     * Method: deleteGraph(long graphId)
     *
     */
    @Test
    public void testDeleteGraphGraphId() throws Exception {
//TODO: Test goes here...


    }

    /**
     *
     * Method: getAllGraphsOfCourse(long courseId)
     *
     */
    @Test
    public void testGetAllGraphsOfCourse() throws Exception {
//TODO: Test goes here...
    }

    /**
     *
     * Method: getJsMindData(long courseGraphId)
     *
     */
    @Test
    public void testGetJsMindData() throws Exception {
//TODO: Test goes here...
    }

    private final String jsmindBase = "{\n" +
            "    /* 元数据，定义思维导图的名称、作者、版本等信息 */\n" +
            "    \"meta\":{\n" +
            "        \"name\":\"jsMind-demo-tree\",\n" +
            "        \"author\":\"hizzgdev@163.com\",\n" +
            "        \"version\":\"0.2\"\n" +
            "    },\n" +
            "    /* 数据格式声明 */\n" +
            "    \"format\":\"node_tree\",\n" +
            "    /* 数据内容 */\n" +
            "    \"data\":{\"id\":\"root\",\"topic\":\"jsMind\",\"children\":[\n" +
            "        {\"id\":\"easy\",\"topic\":\"Easy\",\"direction\":\"left\",\"expanded\":false,\"children\":[\n" +
            "            {\"id\":\"easy1\",\"topic\":\"Easy to show\"},\n" +
            "            {\"id\":\"easy2\",\"topic\":\"Easy to edit\"}\n" +
            "        ]},\n" +
            "        {\"id\":\"open\",\"topic\":\"Open Source\",\"direction\":\"right\",\"expanded\":true,\"children\":[\n" +
            "            {\"id\":\"open1\",\"topic\":\"on GitHub\"},\n" +
            "            {\"id\":\"open2\",\"topic\":\"BSD License\"}\n" +
            "        ]}\n" +
            "    ]}\n" +
            "}";

    private final String jsmindDel = "{\n" +
            "    /* 元数据，定义思维导图的名称、作者、版本等信息 */\n" +
            "    \"meta\":{\n" +
            "        \"name\":\"jsMind-demo-tree\",\n" +
            "        \"author\":\"hizzgdev@163.com\",\n" +
            "        \"version\":\"0.2\"\n" +
            "    },\n" +
            "    /* 数据格式声明 */\n" +
            "    \"format\":\"node_tree\",\n" +
            "    /* 数据内容 */\n" +
            "    \"data\":{\"id\":\"root\",\"topic\":\"jsMind\",\"children\":[\n" +
            "        {\"id\":\"easy\",\"topic\":\"Easy\",\"direction\":\"left\",\"expanded\":false,\"children\":[\n" +
            "            {\"id\":\"easy1\",\"topic\":\"Easy to show\"},\n" +
            "            {\"id\":\"easy2\",\"topic\":\"Easy to edit\"}\n" +
            "        ]}\n" +
            "    ]}\n" +
            "}";

    private final String jsmindAdd = "{\n" +
            "    /* 元数据，定义思维导图的名称、作者、版本等信息 */\n" +
            "    \"meta\":{\n" +
            "        \"name\":\"jsMind-demo-tree\",\n" +
            "        \"author\":\"hizzgdev@163.com\",\n" +
            "        \"version\":\"0.2\"\n" +
            "    },\n" +
            "    /* 数据格式声明 */\n" +
            "    \"format\":\"node_tree\",\n" +
            "    /* 数据内容 */\n" +
            "    \"data\":{\"id\":\"root\",\"topic\":\"jsMind\",\"children\":[\n" +
            "        {\"id\":\"easy\",\"topic\":\"Easy\",\"direction\":\"left\",\"expanded\":false,\"children\":[\n" +
            "            {\"id\":\"easy1\",\"topic\":\"Easy to show\"},\n" +
            "            {\"id\":\"easy2\",\"topic\":\"Easy to edit\"}\n" +
            "        ]},\n" +
            "        {\"id\":\"open\",\"topic\":\"Open Source\",\"direction\":\"right\",\"expanded\":true,\"children\":[\n" +
            "            {\"id\":\"open1\",\"topic\":\"on GitHub\"},\n" +
            "            {\"id\":\"open2\",\"topic\":\"BSD License\"}\n" +
            "        ]},\n" +
            "        {\"id\":\"difficult\",\"topic\":\"Easy\",\"direction\":\"left\",\"expanded\":false,\"children\":[\n" +
            "            {\"id\":\"difficult1\",\"topic\":\"Easy to show\"},\n" +
            "            {\"id\":\"difficult2\",\"topic\":\"Easy to edit\"}\n" +
            "        ]}\n" +
            "    ]}\n" +
            "}";

    private final String jsMindMod = "{\n" +
            "    /* 元数据，定义思维导图的名称、作者、版本等信息 */\n" +
            "    \"meta\":{\n" +
            "        \"name\":\"jsMind-demo-tree\",\n" +
            "        \"author\":\"hizzgdev@163.com\",\n" +
            "        \"version\":\"0.2\"\n" +
            "    },\n" +
            "    /* 数据格式声明 */\n" +
            "    \"format\":\"node_tree\",\n" +
            "    /* 数据内容 */\n" +
            "    \"data\":{\"id\":\"root\",\"topic\":\"jsMind\",\"children\":[\n" +
            "        {\"id\":\"easy\",\"topic\":\"Easy\",\"direction\":\"left\",\"expanded\":false,\"children\":[\n" +
            "            {\"id\":\"easy1\",\"topic\":\"Easy to show\"},\n" +
            "            {\"id\":\"easy2\",\"topic\":\"Easy to edit\"}\n" +
            "        ]},\n" +
            "        {\"id\":\"open\",\"topic\":\"Open Source\",\"direction\":\"right\",\"expanded\":true,\"children\":[\n" +
            "            {\"id\":\"open1\",\"topic\":\"not on GitHub\"},\n" +
            "            {\"id\":\"open2\",\"topic\":\"not BSD License\"}\n" +
            "        ]}\n" +
            "    ]}\n" +
            "}";




}
