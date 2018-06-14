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

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;




@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
//@Transactional
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
        Teacher teacher = new Teacher(1, "teacher", "123", "2@2.com");
        Course course = new Course("SOFT13", "web", 2l,  teacher);
        student.addCourse(course);
        teacher.addCourse(course);
        userRepository.save(student);
        userRepository.save(teacher);
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

        User user = userRepository.findById(1l).get();
        GraphMetaResp graphMetaResp = graphService.createNewGraph(
                user, "test", "some description", "", 2);
        long graphId = graphMetaResp.getId();

        Set<Node> oldNodes = graphRepository.findById(graphId).get().getNodeSet();

        graphService.updateGraph(graphId, jsmind);

        Set<Node> newNodes = graphRepository.findById(graphId).get().getNodeSet();

        assertTrue(newNodes.size() > 0);




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

    private final String jsmind = "{\n" +
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
}
