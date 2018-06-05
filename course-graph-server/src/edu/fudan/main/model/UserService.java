package edu.fudan.main.model;

import edu.fudan.main.domain.User;
import edu.fudan.main.exception.EmailOrPasswordException;
import edu.fudan.main.repository.TokenRepository;
import edu.fudan.main.repository.UserRepository;
import edu.fudan.main.domain.TokenEntry;
import edu.fudan.main.dto.response.AuthenticationResp;
import edu.fudan.main.dto.response.UserPrivateResp;
import edu.fudan.main.dto.response.UserPublicResp;
import edu.fudan.main.exception.UserNotFoundException;

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
}
