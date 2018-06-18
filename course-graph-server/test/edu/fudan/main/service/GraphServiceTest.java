package edu.fudan.main.service;


import edu.fudan.main.domain.*;
import edu.fudan.main.dto.response.GraphMetaResp;
import edu.fudan.main.dto.response.JsmindResp;
import edu.fudan.main.exception.CourseNotFoundException;
import edu.fudan.main.exception.GraphNotFoundException;
import edu.fudan.main.exception.PermissionDeniedException;
import edu.fudan.main.model.GraphService;
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

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
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

    private final long STUDENT_ID = 0;
    private final long TEACHER1_ID = 1;
    private final long TEACHER2_ID = 5;
    private final long COURSE_ID = 2;
    private final long FAKE_COURSE_ID = 3;

    @Before
    public void before() {
        Student student = new Student(STUDENT_ID, "student", "123", "1@1.com");
        Teacher teacher1 = new Teacher(TEACHER1_ID, "teacher1", "123", "2@2.com");
        Teacher teacher2 = new Teacher(TEACHER2_ID, "teacher2", "123", "3@3.com");
        // Course belongs to teacher1
        Course course = new Course("SOFT13", "web", COURSE_ID,  teacher1);
        student.addCourse(course);
        teacher1.addCourse(course);
        userRepository.save(student);
        userRepository.save(teacher1);
        userRepository.save(teacher2);
        courseRepository.save(course);
    }

    @After
    public void after() {
        userRepository.deleteById(STUDENT_ID);
        userRepository.deleteById(TEACHER1_ID);
        userRepository.deleteById(TEACHER2_ID);
        courseRepository.deleteById(COURSE_ID);
    }

    /**
     *
     * Method: createNewGraph(User currentUser, String name, long courseId)
     *
     */
    @Test
    public void testCreateNewGraph() {
        User student = userRepository.findById(STUDENT_ID).orElse(null);
        User teacher1 = userRepository.findById(TEACHER1_ID).orElse(null);
        User teacher2 = userRepository.findById(TEACHER2_ID).orElse(null);
        assertNotNull(student);
        assertNotNull(teacher1);
        assertNotNull(teacher2);

        // Student can not create graph
        try {
            graphService.createNewGraph(student, "test", "some description", "", COURSE_ID);
            fail();
        } catch(PermissionDeniedException ignore){}

        // Other teacher can not create graph
        try {
            graphService.createNewGraph(teacher2, "test", "some description", "", COURSE_ID);
            fail();
        } catch(PermissionDeniedException ignore){}

        // Course must exist
        try {
            graphService.createNewGraph(teacher1, "test", "some description", "", FAKE_COURSE_ID);
            fail();
        } catch (CourseNotFoundException ignore){}


        GraphMetaResp graphMetaResp = graphService.createNewGraph(
                teacher1, "test", "some description", "", COURSE_ID);
        assertEquals(graphMetaResp.getName(), "test");

        // Check if the graph is added to course
        Course course = courseRepository.findById(COURSE_ID).orElse(null);
        Graph graph = graphRepository.findById(graphMetaResp.getId()).orElse(null);
        assertNotNull(graph);
        assertNotNull(course);
        assertEquals(course.getGraphList().size(), 1);
        assertEquals(course.getGraphList().get(0), graph);

    }

    /**
     *
     * Method: updateGraph(Long courseGraphId, String jsMindData)
     *
     */
    @Test
    public void testUpdateGraph() {
        User teacher1 = userRepository.findById(TEACHER1_ID).orElse(null);
        assertNotNull(teacher1);

        GraphMetaResp graphMetaResp = graphService.createNewGraph(
                teacher1, "test", "some description", "", COURSE_ID);
        long graphId = graphMetaResp.getId();

        // At first the node set is empty cause jsmind is empty
        Graph graph = graphRepository.findById(graphId).orElse(null);
        assertNotNull(graph);
        Set<Node> emptyNodes = graph.getNodeSet();
        assertEquals(emptyNodes.size(), 0);

        // ========  TEST JSMIND_BASE   ==========

        // Update the graph by setting jsmind to `JSMIND_BASE` which has 7 node
        graphService.updateJsmind(teacher1, graphId, JSMIND_BASE);

        // Now test the graph
        graph = graphRepository.findById(graphId).orElse(null);
        assertNotNull(graph);
        Set<Node> baseNodes = graph.getNodeSet();
        String jsmindData = graph.getJsMindData();
        // Test jsmind data
        assertEquals(jsmindData, JSMIND_BASE);
        // Test node size
        assertEquals(7, baseNodes.size());


//        // ========  TEST JSMIND_ADD   ==========

        // Update the graph by setting jsmind to `JSMIND_ADD` which add 3 node on the base of JSMIND_BASE
        graphService.updateJsmind(teacher1, graphId, JSMIND_ADD);

        // Now test graph
        graph = graphRepository.findById(graphId).orElse(null);
        assertNotNull(graph);
        Set<Node> addNodes = graph.getNodeSet();
        jsmindData = graph.getJsMindData();
        // Check jsMindData and node size
        assertEquals(jsmindData, JSMIND_ADD);
        assertEquals(10, addNodes.size());
        // Check intersection
        Set<Node> intersection = new HashSet<>(addNodes);
        intersection.retainAll(baseNodes);
        assertEquals(7, intersection.size());



//         ========  TEST JSMIND_DEL   ==========

//         Update the graph by setting jsmind to `JSMIND_DEL` which delete 3 nodes on the base of JSMIND_BASE
        graphService.updateJsmind(teacher1, graphId, JSMIND_DEL);

        // Now test the graph
        graph = graphRepository.findById(graphId).orElse(null);
        assertNotNull(graph);
        Set<Node> delNodes = graph.getNodeSet();
        jsmindData = graph.getJsMindData();
        // Test jsmind data
        assertEquals(jsmindData, JSMIND_DEL);
        // Test node size
        assertEquals(4, delNodes.size());
        // Check intersection of two sets
         intersection = (new HashSet<>(baseNodes));
        intersection.retainAll(delNodes);
        assertEquals(4, intersection.size());

//         ========  TEST jsmindMod   ==========

        // Update the graph by setting jsmind to `jsmindMod` which modified 2 node on the base of JSMIND_BASE
        graphService.updateJsmind(teacher1, graphId, JSMIND_MOD);

        // Now test graph
        graph = graphRepository.findById(graphId).orElse(null);
        assertNotNull(graph);
        Set<Node> modNodes = graph.getNodeSet();
        jsmindData = graph.getJsMindData();

        // Check jsMindData and node size
        assertEquals(jsmindData, JSMIND_MOD);
        assertEquals(7, modNodes.size());
        // Check intersection
        intersection = new HashSet<>(modNodes);
        intersection.retainAll(baseNodes);
        assertEquals(7, intersection.size());
    }

    /**
     *
     * Method: deleteGraph(User currentUser, long graphId)
     *
     */
    @Test
    public void testDeleteGraph() {
        User teacher1 = userRepository.findById(TEACHER1_ID).orElse(null);
        User teacher2 = userRepository.findById(TEACHER2_ID).orElse(null);
        assertNotNull(teacher1);
        assertNotNull(teacher2);

        GraphMetaResp graphMetaResp = graphService.createNewGraph(
                teacher1, "test", "test map", JSMIND_BASE, COURSE_ID);
        long graphId = graphMetaResp.getId();
       // graphService.updateGraph(graphId, JSMIND_BASE);

        // If graph doesn't exist, throw exception
        try {
            graphService.deleteGraph(teacher1, -1); // Fake graphId
            fail();
        } catch(GraphNotFoundException ignore){}

        // If the current login user isn't the teacher/owner of the graph, throw exception
        try {
           graphService.deleteGraph(teacher2, graphId);
           fail();
        } catch(PermissionDeniedException ignore){}

        // Before delete
        Graph graph = graphRepository.findById(graphId).orElse(null);
        assertNotNull(graph);
        Set<Node> nodes = graph.getNodeSet();
        assertEquals(7, nodes.size());
        Course course = courseRepository.findById(COURSE_ID).orElse(null);
        assertNotNull(course);
        assertEquals(1, course.getGraphList().size());
        assertEquals(graph, course.getGraphList().get(0));

        graphService.deleteGraph(teacher1, graphId);

        // After delete, all nodes related to the graph including the graph itself will disappear
        assertFalse(graphRepository.findById(graphId).isPresent());
        for(Node node: nodes){
            assertFalse(nodeRepository.findById(node.getNodeId()).isPresent());
        }
        course = courseRepository.findById(COURSE_ID).orElse(null);
        assertNotNull(course);
//        assertEquals(0, course.getGraphList().size());
    }

    /**
     *
     * Method: getAllGraphsOfCourse(long courseId)
     *
     */
    @Test
    public void testGetAllGraphsOfCourse() {
        User teacher1 = userRepository.findById(TEACHER1_ID).orElse(null);
        assertNotNull(teacher1);

        GraphMetaResp graphMetaResp1 = graphService.createNewGraph(
                teacher1, "test1", "test map1", JSMIND_BASE, COURSE_ID);
        GraphMetaResp graphMetaResp2 = graphService.createNewGraph(
                teacher1, "test2", "test map2", JSMIND_DEL, COURSE_ID);

        // Wrong course id
        try {
            graphService.getAllGraphsOfCourse(teacher1,-1); // Fake courseId
            fail();
        } catch(CourseNotFoundException ignore){}

        List<GraphMetaResp> graphMetaRespList = graphService.getAllGraphsOfCourse(teacher1, COURSE_ID);
        // Course has 2 graphs
        assertEquals(2, graphMetaRespList.size());

        // Sort graph by name
        graphMetaRespList.sort(Comparator.comparing(GraphMetaResp::getName));

        assertEquals(graphMetaRespList.get(0).getId(), graphMetaResp1.getId());
        assertEquals(graphMetaRespList.get(0).getName(), "test1");
        assertEquals(graphMetaRespList.get(0).getDescription(), "test map1");
        assertEquals(graphMetaRespList.get(1).getId(), graphMetaResp2.getId());
        assertEquals(graphMetaRespList.get(1).getName(), "test2");
        assertEquals(graphMetaRespList.get(1).getDescription(), "test map2");

    }

    /**
     *
     * Method: getJsMindData(long courseGraphId)
     *
     */
    @Test
    public void testGetJsMindData() {
        User teacher1 = userRepository.findById(TEACHER1_ID).orElse(null);
        assertNotNull(teacher1);

        GraphMetaResp graphMetaResp = graphService.createNewGraph(
                teacher1, "test", "test map", JSMIND_BASE, COURSE_ID);
        long graphId = graphMetaResp.getId();

        // Wrong graph id
        try {
            graphService.getJsmind(-1);
            fail();
        } catch (GraphNotFoundException ignore){}

        JsmindResp jsmindResp = graphService.getJsmind(graphId);

        assertEquals(jsmindResp.getJsmind(), JSMIND_BASE);
    }

    private final String JSMIND_BASE = "{\n" +
            "    /* 元数据，定义思维导图的名称、作者、版本等信息 */\n" +
            "    \"meta\":{\n" +
            "        \"name\":\"jsMind-demo-tree\",\n" +
            "        \"author\":\"hizzgdev@163.com\",\n" +
            "        \"version\":\"0.1\"\n" +
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

    private final String JSMIND_DEL = "{\n" +
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

    private final String JSMIND_ADD = "{\n" +
            "    /* 元数据，定义思维导图的名称、作者、版本等信息 */\n" +
            "    \"meta\":{\n" +
            "        \"name\":\"jsMind-demo-tree\",\n" +
            "        \"author\":\"hizzgdev@163.com\",\n" +
            "        \"version\":\"0.3\"\n" +
            "    },\n" +
            "    /* 数据格式声明 */\n" +
            "    \"format\":\"node_tree\",\n" +
            "    /* 数据内容 */\n" +
            "    \"data\":{\"id\":\"root\",\"topic\":\"jsMind\",\"children\":[\n" +
            "        {\"id\":\"easy\",\"topic\":\"Easy\",\"direction\":\"left\",\"expanded\":false,\"children\":[\n" +
            "            {\"id\":\"easy1\",\"topic\":\"Easy1 to show\"},\n" +
            "            {\"id\":\"easy2\",\"topic\":\"Easy2 to edit\"}\n" +
            "        ]},\n" +
            "        {\"id\":\"open\",\"topic\":\"Open Source\",\"direction\":\"right\",\"expanded\":true,\"children\":[\n" +
            "            {\"id\":\"open1\",\"topic\":\"on GitHub\"},\n" +
            "            {\"id\":\"open2\",\"topic\":\"BSD License\"}\n" +
            "        ]},\n" +
            "        {\"id\":\"difficult\",\"topic\":\"Difficult\",\"direction\":\"left\",\"expanded\":false,\"children\":[\n" +
            "            {\"id\":\"difficult1\",\"topic\":\"Difficult to show\"},\n" +
            "            {\"id\":\"difficult2\",\"topic\":\"Difficult to edit\"}\n" +
            "        ]}\n" +
            "    ]}\n" +
            "}";

    private final String JSMIND_MOD = "{\n" +
            "    /* 元数据，定义思维导图的名称、作者、版本等信息 */\n" +
            "    \"meta\":{\n" +
            "        \"name\":\"jsMind-demo-tree\",\n" +
            "        \"author\":\"hizzgdev@163.com\",\n" +
            "        \"version\":\"0.4\"\n" +
            "    },\n" +
            "    /* 数据格式声明 */\n" +
            "    \"format\":\"node_tree\",\n" +
            "    /* 数据内容 */\n" +
            "    \"data\":{\"id\":\"root\",\"topic\":\"jsMind\",\"children\":[\n" +
            "        {\"id\":\"easy\",\"topic\":\"Easy\",\"direction\":\"left\",\"expanded\":false,\"children\":[\n" +
            "            {\"id\":\"easy1\",\"topic\":\"Easy1 to show\"},\n" +
            "            {\"id\":\"easy2\",\"topic\":\"Easy2 to edit\"}\n" +
            "        ]},\n" +
            "        {\"id\":\"open\",\"topic\":\"Open Source\",\"direction\":\"right\",\"expanded\":true,\"children\":[\n" +
            "            {\"id\":\"open1\",\"topic\":\"not on GitHub\"},\n" +
            "            {\"id\":\"open2\",\"topic\":\"not BSD License\"}\n" +
            "        ]}\n" +
            "    ]}\n" +
            "}";

}
