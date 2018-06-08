package edu.fudan.main.repository;

import edu.fudan.main.domain.Student;
import edu.fudan.main.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;


    @Test
    public void testCreateUser() {
        assertThat(userRepository.findById(1L).isPresent()).isFalse();

        User user = new Student(1, "2", "3", "4");
        userRepository.save(user);

        assertThat(userRepository.findById(1L).isPresent()).isTrue();
        assertThat(userRepository.findById(2L).isPresent()).isFalse();
    }

}