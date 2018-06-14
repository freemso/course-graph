package edu.fudan.main.service;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class GraphServiceTest {

    @Before
    public void before() {

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
//TODO: Test goes here...
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

}
