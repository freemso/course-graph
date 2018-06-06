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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Test
    public void testCreateUser() {
        assertThat(userRepository.findById(1L).isPresent()).isFalse();

        Student user = new Student(1, "2", "3", "4");
        studentRepository.save(user);
        assertTrue(userRepository.findById((long) 1).isPresent());
        assertThat(userRepository.findById(1L).isPresent()).isTrue();
        assertThat(userRepository.findById(2L).isPresent()).isFalse();
    }

}