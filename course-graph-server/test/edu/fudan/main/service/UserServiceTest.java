package edu.fudan.main.service;

import edu.fudan.main.domain.Student;
import edu.fudan.main.domain.TokenEntry;
import edu.fudan.main.domain.User;
import edu.fudan.main.domain.UserType;
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
    public void setup() {
        User student = new Student(1, "user1", "1234", "stu@fudan.edu.cn");
        userRepository.save(student);
    }

    @Test
    public void testLogin() {
        //stu createToken
        //wrong email
        loginFailAssertion("stu@sjtu.edu.cn", "1234");
        //wrong password
        loginFailAssertion("stu@fudan.edu.cn", "123");
        //wrong email and password
        loginFailAssertion("stu@sjtu.edu.cn", "2");
        //succeed
        AuthenticationResp resp = userService.createToken("stu@fudan.edu.cn", "1234");
//        assertNotNull(userService.createToken("stu@fudan.edu.cn", "1234"));
        // Every time we create a token, the old token related to that user is deleted
        assertNotNull(resp);

        // The token in AuthenticationResp is actually the combination of token and id
        // so we only need to pass it to getAuthentication() to get the tokenEntry
        TokenEntry tokenEntry = tokenRepository.getToken(resp.getAuthentication());
        assertTrue(tokenRepository.checkToken(tokenEntry));

        // Test token override
        // create a token again
        assertNotNull(userService.createToken("stu@fudan.edu.cn", "1234"));
        TokenEntry oldTokenEntry = tokenRepository.getToken(resp.getAuthentication());
        // old token will be invalid
        assertFalse(tokenRepository.checkToken(oldTokenEntry));
    }

    @Test
    public void testLogout() {
        //createToken
        AuthenticationResp resp = userService.createToken("stu@fudan.edu.cn", "1234");
        assertNotNull(resp);

        TokenEntry tokenEntry = tokenRepository.getToken(resp.getAuthentication());
        assertTrue(tokenRepository.checkToken(tokenEntry));

        //deleteToken
        userService.deleteToken(1);
        TokenEntry oldTokenEntry = tokenRepository.getToken(resp.getAuthentication());
        assertFalse(tokenRepository.checkToken(oldTokenEntry));
    }


    @Test
    public void testUpdate() {
        User currentUser = userRepository.findById(1L).orElse(null);
        assertNotNull(currentUser);

        //update failed, wrong password
        try {
            userService.updateUser(currentUser, "newName", "newMail", "12345", "12345");
            fail();
        } catch (EmailOrPasswordException ignored) {
            // ignore it
        }
        User modifiedUser = userRepository.findById(1L).orElse(null);
        assertNotNull(modifiedUser);
        assertEquals(currentUser.hashCode(), modifiedUser.hashCode());

        //update succeeded
        userService.updateUser(currentUser, "newName", "newEmail", "1234", "12345");
        modifiedUser = userRepository.findById(1L).orElse(null);
        assertNotNull(modifiedUser);
        assertEquals(modifiedUser.getName(), "newName");
        assertEquals(modifiedUser.getEmail(), "newEmail");
        assertEquals(modifiedUser.getPassword(), "12345");
    }

    @Test
    public void testRegister() {

        //createUser failure caused by duplicate email
        try {
            userService.createUser("stu@fudan.edu.cn", "x", "12", UserType.STUDENT);
            fail();
        } catch(EmailConflictException ignore){
            // ignore it
        }

        //createUser successfully
        UserPrivateResp resp = userService.createUser("stu2@fudan.edu.cn", "x", "12", UserType.STUDENT);
        assertNotNull(resp);
        User user = userRepository.findByEmail("stu2@fudan.edu.cn").orElse(null);
        assertNotNull(user);
        assertEquals(user.getPassword(), "12");
        assertEquals(user.getEmail(), "stu2@fudan.edu.cn");
        assertEquals(user.getName(), "x");

    }

    @Test
    public void testGetUserPublicData() {
        try {
            userService.getUserPublic(0);
            fail();
        } catch(UserNotFoundException ignore){
            // ignore it
        }

        UserPublicResp resp = userService.getUserPublic(1);
        assertNotNull(resp);

        User user = userRepository.findById(1L).orElse(null);
        assertNotNull(user);
        assertEquals(user.getName(), resp.getName());
        assertEquals(user.getId(), resp.getId());
        assertEquals(user.getType(), resp.getType());

    }


    @Test
    public void testGerUserPrivateData() {
        try {
            userService.getUserPrivate(0);
            fail();
        } catch(UserNotFoundException ignore){
            // ignore it
        }
        UserPrivateResp resp = userService.getUserPrivate(1);
        User user = userRepository.findById(1L).orElse(null);
        assertNotNull(user);
        assertNotNull(resp);
        assertEquals(user.getEmail(), resp.getEmail());
    }


    private void loginFailAssertion(String email, String password) {
        try {
            userService.createToken(email, password);
            fail();
        } catch (EmailOrPasswordException e) {
            assertTrue(true);
        }
    }

}
