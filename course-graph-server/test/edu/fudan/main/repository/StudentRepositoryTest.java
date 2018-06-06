package edu.fudan.main.repository;

import edu.fudan.main.domain.Student;
import edu.fudan.main.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Collection;
import java.util.Optional;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class StudentRepositoryTest {
    @Autowired
    private StudentRepository studentRepository;
    private Student student;
    @Before
    public void init(){
        student =  new Student(1212, "junit_test_student", "1", "1");
    }

    @Test
    public void testFindById(){
        Optional<User> user = studentRepository.findById((long) 1212);
        assertTrue(user.isPresent());
        assertEquals(student, (Student)user.get());
    }
}
