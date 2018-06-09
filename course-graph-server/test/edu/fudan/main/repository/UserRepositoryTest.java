package edu.fudan.main.repository;

import edu.fudan.main.domain.Student;
import edu.fudan.main.domain.Teacher;
import edu.fudan.main.domain.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    private Student student;
    private Teacher teacher;

    @Before
    public void set(){
        student = new Student(1, "test_student", "pwd", "email1");
        teacher = new Teacher(2, "test_teacher", "pwd", "email2");
        userRepository.save(student);
        userRepository.save(teacher);
    }

    @Test
    public void testFindById(){
        Optional<User> user1 = userRepository.findById(1L);
        assertTrue(user1.isPresent());
        assertEquals(student, user1.get());

        Optional<User> user2 = userRepository.findById(2L);
        assertTrue(user2.isPresent());
        assertEquals(teacher, user2.get());
    }

    @Test
    public void testFindByEmail(){
        Optional<User> user1 = userRepository.findByEmail("email1");
        assertTrue(user1.isPresent());
        assertEquals(student, user1.get());

        Optional<User> user2 = userRepository.findByEmail("email2");
        assertTrue(user2.isPresent());
        assertEquals(teacher, user2.get());
    }


    @Test
    public void testFindByName(){
        List<User> users1 = userRepository.findByName("test_student");
        assertTrue(users1.size() == 1);
        assertEquals(users1.get(0), student);

        List<User> users2 = userRepository.findByName("test_teacher");
        assertTrue(users2.size() == 1);
        assertEquals(users2.get(0), teacher);

        student.setName("test_teacher");
        userRepository.save(student);
        List<User> users3 = userRepository.findByName("test_teacher");
        assertTrue(users3.size() == 2);
        if(users3.get(0).equals(student))
            assertEquals(users3.get(1), (teacher));
        else if(users3.get(0).equals(teacher))
            assertEquals(users3.get(1), student);
        else
            assert false;

    }

}