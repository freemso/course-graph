package edu.fudan.main.service;

import edu.fudan.main.domain.*;
import edu.fudan.main.dto.response.GraphMetaResp;
import edu.fudan.main.exception.CourseNotFoundException;
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
    public void before() {
        Student student = new Student(0, "student", "123", "1@1.com");
        Teacher teacher = new Teacher(1, "teacher", "123", "2@2.com");
        Course course = new Course("SOFT13", "web", 2L,  teacher);
        student.addCourse(course);
        teacher.addCourse(course);
        userRepository.save(student);
        userRepository.save(teacher);
        courseRepository.save(course);
    }

    @After
    public void after() {
    }

    /**
     *
     * Method: createNewGraph(User currentUser, String name, long courseId)
     *
     */
    @Test
    public void testCreateNewGraph() {
        User student = userRepository.findById(0L).orElse(null);
        User teacher = userRepository.findById(1L).orElse(null);

        // Student can not create graph
        try {
            graphService.createNewGraph(student, "test", "some description", "", 2);
            fail();
        } catch(PermissionDeniedException ignore) {}

        // Course id does not exist
        try {
            graphService.createNewGraph(teacher, "test", "some description", "", 3);
            fail();
        } catch (CourseNotFoundException ignore){}

        // Teacher can create a graph
        GraphMetaResp graphMetaResp = graphService.createNewGraph(
                teacher, "test", "some description", "", 2);
        assertEquals(graphMetaResp.getName(), "test");

        // Check if the graph is added to course
        Course course = courseRepository.findById(2L).orElse(null);
        Graph graph = graphRepository.findById(graphMetaResp.getId()).orElse(null);
        assertNotNull(graph);
        assertNotNull(course);
        assertEquals(course.getGraphList().size(), 1);
        assertEquals(course.getGraphList().get(0), graph);
    }


    /**
     *
     * Method: updateJsmind(Long courseGraphId, String jsMindData)
     *
     */
    @Test
    public void testUpdateJsmind() {
        User teacher = userRepository.findById(1L).orElse(null);
        GraphMetaResp graphMetaResp = graphService.createNewGraph(
                teacher, "test", "some description", "", 2);

        long graphId = graphMetaResp.getId();

        // At first the node set is empty cause jsmind is empty
        Graph graph = graphRepository.findById(graphId).orElse(null);
        assertNotNull(graph);
        Set<Node> oldNodes = graph.getNodeSet();
        assertEquals(oldNodes.size(), 0);

        // Update jsmind with some actual jsmind data
        graphService.updateJsmind(teacher, graphId, jsmind1);

        // Now that there should be some nodes in node set
        graph = graphRepository.findById(graphId).orElse(null);
        assertNotNull(graph);
        Set<Node> newNodes = graph.getNodeSet();
        int nodeSize = newNodes.size();
        assertTrue(nodeSize > 0);

        // Update it again with another jsmind data
        // Note that this jsmind misses two nodes compared with the one above
        graphService.updateJsmind(teacher, graphId, jsmind2);

        // Now that there should be two nodes less than last one
        graph = graphRepository.findById(graphId).orElse(null);
        assertNotNull(graph);
        Set<Node> newnewNodes = graph.getNodeSet();
        assertEquals(newnewNodes.size(), nodeSize - 2);

    }



    /**
     *
     * Method: deleteGraph(User currentUser, long graphId)
     *
     */
    @Test
    public void testDeleteGraphForCurrentUserGraphId() throws Exception {
//TODO: Test goes here...
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

    private final String jsmind1 = "{\n" +
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
            "            {\"id\":\"easy2\",\"topic\":\"Easy to edit\"},\n" +
            "            {\"id\":\"easy3\",\"topic\":\"Easy to store\"},\n" +
            "            {\"id\":\"easy4\",\"topic\":\"Easy to embed\"}\n" +
            "        ]},\n" +
            "        {\"id\":\"open\",\"topic\":\"Open Source\",\"direction\":\"right\",\"expanded\":true,\"children\":[\n" +
            "            {\"id\":\"open1\",\"topic\":\"on GitHub\"},\n" +
            "            {\"id\":\"open2\",\"topic\":\"BSD License\"}\n" +
            "        ]},\n" +
            "        {\"id\":\"powerful\",\"topic\":\"Powerful\",\"direction\":\"right\",\"children\":[\n" +
            "            {\"id\":\"powerful1\",\"topic\":\"Base on Javascript\"},\n" +
            "            {\"id\":\"powerful2\",\"topic\":\"Base on HTML5\"},\n" +
            "            {\"id\":\"powerful3\",\"topic\":\"Depends on you\"}\n" +
            "        ]},\n" +
            "        {\"id\":\"other\",\"topic\":\"test node\",\"direction\":\"left\",\"children\":[\n" +
            "            {\"id\":\"other1\",\"topic\":\"I'm from local variable\"},\n" +
            "            {\"id\":\"other2\",\"topic\":\"I can do everything\"}\n" +
            "        ]}\n" +
            "    ]}\n" +
            "}";

    // miss two nodes compared with jsmind1
    private final String jsmind2 = "{\n" +
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
            "            {\"id\":\"easy1\",\"topic\":\"Easy to show\"},\n" +
            "            {\"id\":\"easy2\",\"topic\":\"Easy to edit\"},\n" +
            "            {\"id\":\"easy3\",\"topic\":\"Easy to store\"}\n" +
            "        ]},\n" +
            "        {\"id\":\"open\",\"topic\":\"Open Source\",\"direction\":\"right\",\"expanded\":true,\"children\":[\n" +
            "            {\"id\":\"open1\",\"topic\":\"on GitHub\"},\n" +
            "            {\"id\":\"open2\",\"topic\":\"BSD License\"}\n" +
            "        ]},\n" +
            "        {\"id\":\"powerful\",\"topic\":\"Powerful\",\"direction\":\"right\",\"children\":[\n" +
            "            {\"id\":\"powerful1\",\"topic\":\"Base on Javascript\"},\n" +
            "            {\"id\":\"powerful2\",\"topic\":\"Base on HTML5\"}\n" +
            "        ]},\n" +
            "        {\"id\":\"other\",\"topic\":\"test node\",\"direction\":\"left\",\"children\":[\n" +
            "            {\"id\":\"other1\",\"topic\":\"I'm from local variable\"},\n" +
            "            {\"id\":\"other2\",\"topic\":\"I can do everything\"}\n" +
            "        ]}\n" +
            "    ]}\n" +
            "}";
}
