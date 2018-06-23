package edu.fudan.repository;

import edu.fudan.domain.Student;
import edu.fudan.domain.Teacher;
import edu.fudan.domain.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


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
        student = new Student(1L, "test_student", "pwd", "email1");
        teacher = new Teacher(2L, "test_teacher", "pwd", "email2");
        userRepository.save(student);
        userRepository.save(teacher);
    }

    @After
    public void after() {
        userRepository.deleteById(1L);
        userRepository.deleteById(2L);
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
        assertEquals(1, users1.size());
        assertEquals(users1.get(0), student);

        List<User> users2 = userRepository.findByName("test_teacher");
        assertEquals(1, users2.size());
        assertEquals(users2.get(0), teacher);

        student.setName("test_teacher");
        userRepository.save(student);
        List<User> users3 = userRepository.findByName("test_teacher");
        assertEquals(2, users3.size());
        if(users3.get(0).equals(student))
            assertEquals(users3.get(1), (teacher));
        else if(users3.get(0).equals(teacher))
            assertEquals(users3.get(1), student);
        else
            assert false;

    }

}