package edu.fudan.service;

import edu.fudan.domain.*;
import edu.fudan.dto.response.QuestionResp;
import edu.fudan.exception.PermissionDeniedException;
import edu.fudan.model.GraphService;
import edu.fudan.model.QuestionService;
import edu.fudan.repository.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/** 
* QuestionService Tester. 
* 
* @author <Authors name> 
* @since <pre>六月 18, 2018</pre> 
* @version 1.0 
*/


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class QuestionServiceTest {

    private final long STUDENT_ID = 0;
    private final long TEACHER_ID = 1;
    private final long GRAPH_ID = 4;
    private final long TEACHER2_ID = 5;
    private final long COURSE_ID = 2;
    private final long FAKE_COURSE_ID = 3;

    @Autowired
    private NodeRepository nodeRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private GraphRepository graphRepository;

    @Autowired
    QuestionService questionService;

    @Autowired
    GraphService graphService;

    @Before
public void before() throws Exception {
    Teacher teacher = new Teacher(TEACHER_ID, "test_teacher", "123", "test@fudan.edu.cn");
    Teacher teacher1 = new Teacher(TEACHER2_ID, "test_teacher2", "123", "test@fudan.edu.cn");
    Student student = new Student(STUDENT_ID, "test_student", "stu123", "stu@fudane.edu.cn");
    Course course = new Course("test1212", "test_course", COURSE_ID, teacher);
    Graph graph = new Graph(GRAPH_ID, "test_graph", "test", JSMIND, course);
    userRepository.save(teacher);
    userRepository.save(teacher1);
    userRepository.save(student);
    courseRepository.save(course);
    graphRepository.save(graph);
    graphService.updateJsmind(teacher, graph.getGraphId(), JSMIND);


} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: getAllQuestionsOfNode(User currentUser, String nodeId) 
* 
*/ 
@Test
public void testGetAllQuestionsOfNode() throws Exception { 
//TODO: Test goes here...



} 

/** 
* 
* Method: createQuestion(User currentUser, String nodeId, String description, QuestionType type, List<Choice> choices, String answer) 
* 
*/ 
@Test
public void testCreateQuestion() throws Exception { 
//TODO: Test goes here...

    //user permission check
    User student = userRepository.findById(STUDENT_ID).get();
    User teacher = userRepository.findById(TEACHER_ID).get();
    User teacher1 = userRepository.findById(TEACHER2_ID).get();
    Node node = nodeRepository.findById("root").get();
    List<Choice> choices = new ArrayList<>();
    choices.add(new Choice("A", "test"));

    try{
        questionService.createQuestion(student, "root", "test", QuestionType.MULTIPLE_CHOICE, choices, "wrong");
        assert false;
    }catch(PermissionDeniedException e){
        assert true;
    }

    try{
        questionService.createQuestion(teacher1, "root", "test", QuestionType.MULTIPLE_CHOICE, choices, "wrong");
        assert false;
    }catch(PermissionDeniedException e){
        assert true;
    }
    QuestionResp questionResp = questionService.createQuestion(teacher, "root", "test", QuestionType.MULTIPLE_CHOICE, choices, "c");

    //create a multiple choice question


    //create a short answer question


} 

/** 
* 
* Method: createAnswerEntry(User currentUser, long questionId, String answer) 
* 
*/ 
@Test
public void testCreateAnswerEntry() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: deleteQuestion(long questionId) 
* 
*/ 
@Test
public void testDeleteQuestionQuestionId() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: deleteQuestion(User currentUser, long questionId) 
* 
*/ 
@Test
public void testDeleteQuestionForCurrentUserQuestionId() throws Exception { 
//TODO: Test goes here... 
} 


/** 
* 
* Method: deleteQuestion(Question question) 
* 
*/ 
@Test
public void testDeleteQuestion() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = QuestionService.getClass().getMethod("deleteQuestion", Question.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
}

    private final String JSMIND = "{\n" +
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


} 
