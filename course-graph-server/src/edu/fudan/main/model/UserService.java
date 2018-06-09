package edu.fudan.main.model;

import edu.fudan.main.domain.*;
import edu.fudan.main.dto.response.AuthenticationResp;
import edu.fudan.main.dto.response.UserPrivateResp;
import edu.fudan.main.dto.response.UserPublicResp;
import edu.fudan.main.exception.EmailConflictException;
import edu.fudan.main.exception.EmailOrPasswordException;
import edu.fudan.main.exception.UserNotFoundException;
import edu.fudan.main.repository.TokenRepository;
import edu.fudan.main.repository.UserRepository;
import edu.fudan.main.util.RandomIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.InvalidPropertiesFormatException;
import java.util.Optional;
import java.util.regex.Pattern;

import static edu.fudan.main.config.Constants.EMAIL_REGEX;
import static edu.fudan.main.config.Constants.PASSWORD_REGEX;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    private final TokenRepository tokenRepository;

    private final Pattern emailPattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);

    private final Pattern passwordPattern = Pattern.compile(PASSWORD_REGEX);


    @Autowired
    public UserService(UserRepository userRepository, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    /**
     * Given email and password, create a token entry and return it as a authentication response
     * @param email, email of the account
     * @param password, password of the account
     * @return a string of authentication
     * @throws EmailOrPasswordException, when email not exists or password not match
     */
    public AuthenticationResp login(String email, String password) {
        User user = this.userRepository.findByEmail(email).orElseThrow(
                EmailOrPasswordException::new // Email does not exist
        );

        if (!user.getPassword().equals(password)) {  // Wrong password
            throw new EmailOrPasswordException();
        }

        // Generate a token
        TokenEntry tokenEntry = tokenRepository.createToken(user.getId());

        // Generate authentication from this token
        String authentication = tokenRepository.getAuthentication(tokenEntry);

        return new AuthenticationResp(authentication, new UserPublicResp(user));
    }

    /**
     * Given the user id, delete the token from repository
     * @param userId, id of the current user
     */
    public void logout(long userId) {
        tokenRepository.deleteToken(userId);
    }

    /**
     * Get user public data by id.
     * @return the public data of the user
     * @throws UserNotFoundException, when user not found
     */
    public UserPublicResp getUserPublic(long userId) {
        User user = this.userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId) // User not found
        );

        return new UserPublicResp(user);
    }

    /**
     * Get user private data by id
     * @return the private data of the user
     * @throws UserNotFoundException, when user not found
     */
    public UserPrivateResp getUserPrivate(long userId) {
        User user = this.userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId) // User not found
        );

        return new UserPrivateResp(user);
    }

    /**
     * User registration
     * @return user private data
     * @throws EmailConflictException, when email already existed
     */
    public UserPrivateResp register(String email, String name, String password, UserType type) {
        // Check if the email exists
        if (this.userRepository.findByEmail(email).isPresent()) {
            throw new EmailConflictException(email);
        }

        // Create a new id for user
        long newUserId = this.generateRandomId();

        // Create a new user based on the user type
        User newUser = null;
        switch (type) {
            case STUDENT:
                newUser = new Student(newUserId, name, password, email);
                break;
            case TEACHER:
                newUser = new Teacher(newUserId, name, password, email);
                break;
        }

        // Add this newly created user to user repository
        User savedUser = this.userRepository.save(newUser);

        // Return a user private data
        return new UserPrivateResp(savedUser);
    }


    public UserPrivateResp updateUser(User currentUser, String name, String email, String password, String newPassword) {
        // Check if the password matches
        if (! currentUser.getPassword().equals(password)) {
            throw new EmailOrPasswordException();
        }

        if (name != null) {
            // Change name
            User user = userRepository.findById(currentUser.getId()).orElse(null);
            if(user == null)
                return null;
            user.setName(name);
            userRepository.save(user, 0);
            //Optional<User> user = userRepository.findById(currentUser.getId());
//            currentUser = userRepository.updateNameOfUserWithId(currentUser.getId(), name).orElse(currentUser);
        }

//        if (email != null) {
//            // Check email pattern
//            if (! emailPattern.matcher(email).matches()) {
//                throw new MethodArgumentNotValidException(new MethodParameter());
//            }
//
//        }
        // TODO
        return null;

    }


    /**
     * Generate a unique user id
     * @return a user id
     */
    private long generateRandomId() {
        while (true) {
            long randomLong = RandomIdGenerator.getInstance().generateRandomLongId();
            // Check if the id exists as a user id
            if (!this.userRepository.existsById(randomLong)) {
                return randomLong;
            }
        }
    }
}
