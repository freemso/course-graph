package test.java.repository;

import neo4j.repositories.CourseModuleRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


import java.util.LinkedHashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class CourseRepositoryTest {

    @Autowired
    private CourseModuleRepository courseModuleRepository;

    @Test
    public void testFetchCourseModuleTree(){
        LinkedHashMap json = courseModuleRepository.fetchCourseModuleTreeByRootId(12);
    }


}

