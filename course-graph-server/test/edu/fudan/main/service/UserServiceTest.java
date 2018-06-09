package edu.fudan.main.service;

import edu.fudan.main.domain.*;
import edu.fudan.main.dto.response.AuthenticationResp;
import edu.fudan.main.dto.response.UserPrivateResp;
import edu.fudan.main.dto.response.UserPublicResp;
import edu.fudan.main.exception.EmailConflictException;
import edu.fudan.main.exception.EmailOrPasswordException;
import edu.fudan.main.exception.UserNotFoundException;
import edu.fudan.main.model.UserService;
import edu.fudan.main.repository.TokenRepository;
import edu.fudan.main.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;



import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class UserServiceTest {


    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenRepository tokenRepository;

    @Before
    public void set() {
        User student = new Student(1, "user1", "1234", "stu@fudan.edu.cn");
        userRepository.save(student);
    }

    @Test
    public void testLogin() {
        //stu login
        //wrong email
        loginFailAssertion("stu@sjtu.edu.cn", "1234");
        //wrong password
        loginFailAssertion("stu@fudan.edu.cn", "123");
        //wrong email and password
        loginFailAssertion("stu@sjtu.edu.cn", "2");
        //succeed
        AuthenticationResp resp = userService.login("stu@fudan.edu.cn", "1234");
        assertNotNull(userService.login("stu@fudan.edu.cn", "1234"));
        TokenEntry tokenEntry = new TokenEntry(1, resp.getToken());
        assertTrue(tokenRepository.checkToken(tokenEntry));
    }

    @Test
    public void testLogout() {
        //login
        AuthenticationResp resp = userService.login("stu@fudan.edu.cn", "1234");
        assertNotNull(resp);
        TokenEntry tokenEntry = new TokenEntry(1, resp.getToken());
        assertTrue(tokenRepository.checkToken(tokenEntry));
        //logout
        userService.logout(1);
        assertFalse(tokenRepository.checkToken(tokenEntry));
    }


    @Test
    public void testUpdate() {
        //update
        User currentUser = userRepository.findById(1L).get();
        assertNotNull(currentUser);
        //update failed
        UserPrivateResp resp = userService.updateUser(currentUser, "newName", "newMail", "12345", "12345");
        User modifiedUser = userRepository.findById(1L).get();
        assertNotNull(modifiedUser);
        assertEquals(currentUser.hashCode(), modifiedUser.hashCode());

        //update succeeded
        resp = userService.updateUser(currentUser, "newName", "newMail", "1234", "12345");
        modifiedUser = userRepository.findById(1L).get();
        assertNotNull(modifiedUser);
        assertEquals(modifiedUser.getName(), "newName");
        assertEquals(modifiedUser.getEmail(), "newEmail");
        assertEquals(modifiedUser.getPassword(), "12345");
    }

    @Test
    public void testRegister() {

        //register failure caused by duplicate email
        try {
            userService.register("stu@fudan.edu.cn", "x", "12", UserType.STUDENT);
            assert false;
        }catch(EmailConflictException e){
            assert true;
        }

        //register successfully
        UserPrivateResp resp = userService.register("stu2@fudan.edu.cn", "x", "12", UserType.STUDENT);
        assertNotNull(resp);
        User user = userRepository.findByEmail("stu2@fudan.edu.cn").get();
        assertNotNull(user);
        assertEquals(user.getPassword(), "12");
        assertEquals(user.getEmail(), "stu2@fudan.edu.cn");
        assertEquals(user.getName(), "x");

    }

    @Test
    public void testGetUserPublicData() {
        try {
            userService.getUserPublic(0);
            assert false;
        }catch(UserNotFoundException e){
            assert true;
        }

        UserPublicResp resp = userService.getUserPublic(1);
        User user = userRepository.findById(1L).get();
        assertNotNull(user);
        assertNotNull(resp);
        assertEquals(user.getName(), resp.getName());
        assertEquals(user.getId(), resp.getId());
        assertEquals(user.getType(), resp.getType());

    }


    @Test
    public void testGerUserPrivateData() {
        try {
            userService.getUserPrivate(0);
            assert false;
        }catch(UserNotFoundException e){
            assert true;
        }
        UserPrivateResp resp = userService.getUserPrivate(1);
        User user = userRepository.findById(1L).get();
        assertNotNull(user);
        assertNotNull(resp);
        assertEquals(user.getEmail(), resp.getEmail());
    }


    private void loginFailAssertion(String email, String password) {
        try {
            userService.login(email, password);
            assertTrue(false);
        } catch (EmailOrPasswordException e) {
            assertTrue(true);
        }
    }

}
