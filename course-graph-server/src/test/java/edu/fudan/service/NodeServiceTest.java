package edu.fudan.service;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class NodeServiceTest {

    @Before
    public void before() {
    }

    @After
    public void after() {
    }

    /**
     * Method: getAllQuestionsOfNode(User currentUser, String nodeId)
     */
    @Test
    public void testGetAllQuestionsOfNode() {
//TODO: Test goes here...

    }

    /**
     * Method: addQuestionMultipleChoiceOfNode(String nodeId, String description, List<Choice> choices, String answer)
     */
    @Test
    public void testAddQuestionMultipleChoiceOfNode() throws Exception {
//TODO: Test goes here...
    }

    /**
     * Method: addQuestionShortAnswerOfNode(String nodeId, String description)
     */
    @Test
    public void testAddQuestionShortAnswerOfNode() throws Exception {
//TODO: Test goes here...
    }

    /**
     * Method: handleQuestionSubmission(User currentUser, List<QuestionSubmission> submissions)
     */
    @Test
    public void testHandleQuestionSubmission() throws Exception {
//TODO: Test goes here...
    }

    /**
     * Method: deleteQuestion(long questionId)
     */
    @Test
    public void testDeleteQuestion() throws Exception {
//TODO: Test goes here...
    }

    /**
     * Method: addResourceOfNode()
     */
    @Test
    public void testAddResourceOfNode() throws Exception {
//TODO: Test goes here...
    }

    /**
     * Method: getAllResourcesOfNode()
     */
    @Test
    public void testGetAllResourcesOfNode() throws Exception {
//TODO: Test goes here...
    }

    /**
     * Method: deleteResource()
     */
    @Test
    public void testDeleteResource() throws Exception {
//TODO: Test goes here...
    }

    /**
     * Method: addLecture()
     */
    @Test
    public void testAddLecture() throws Exception {
//TODO: Test goes here...
    }

    /**
     * Method: getAllLecturesOfNode()
     */
    @Test
    public void testGetAllLecturesOfNode() throws Exception {
//TODO: Test goes here...
    }

    /**
     * Method: deleteLecture()
     */
    @Test
    public void testDeleteLecture() throws Exception {
//TODO: Test goes here...
    }


    /**
     * Method: getNodesFromMindData(JSONObject mindNode, List<Node> newNodes)
     */
    @Test
    public void testGetNodesFromMindData() throws Exception {
//TODO: Test goes here...
/*
try {
   Method method = NodeService.getClass().getMethod("getNodesFromMindData", JSONObject.class, List<Node>.class);
   method.setAccessible(true);
   method.invoke(<Object>, <Parameters>);
} catch(NoSuchMethodException e) {
} catch(IllegalAccessException e) {
} catch(InvocationTargetException e) {
}
*/
    }
}
