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
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final TokenRepository tokenRepository;


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
     * @param userId, id of the user to query
     * @return the public data of the user
     */
    public UserPublicResp getUserPublic(long userId) {
        User user = this.userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId) // User not found
        );

        return new UserPublicResp(user);
    }

    /**
     * Get user private data by id
     * @param userId, id of the user
     * @return the private data of the user
     */
    public UserPrivateResp getUserPrivate(long userId) {
        User user = this.userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId) // User not found
        );

        return new UserPrivateResp(user);
    }

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


    /**
     * Generate a unique user id
     * @return a user id
     */
    private long generateRandomId() {
        while (true) {
            long randomLong = RandomIdGenerator.getInstance().generateRandomLongId();
            // Check if the id exists as a user id
            if (!this.userRepository.findById(randomLong).isPresent()) {
                return randomLong;
            }
        }
    }
}
