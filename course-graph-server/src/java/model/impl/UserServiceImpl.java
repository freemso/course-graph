package main.java.model.impl;

import main.java.config.ResponseExceptions.EmailOrPasswordException;
import main.java.domain.TokenEntry;
import main.java.domain.User;
import main.java.dto.RespLoginDTO;
import main.java.repository.TokenRepository;
import main.java.repository.UserRepository;
import main.java.model.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final TokenRepository tokenRepository;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }


    @Override
    public RespLoginDTO login(String email, String password) {
        User user = this.userRepository.findByEmail(email).orElseThrow(
                EmailOrPasswordException::new // User does not exist
        );

        if (!user.getPassword().equals(password)) {  // Wrong password
            throw new EmailOrPasswordException();
        }

        // Generate a token
        TokenEntry tokenEntry = tokenRepository.createToken(user.getId());

        // Generate authentication from this token
        String authentication = tokenRepository.getAuthentication(tokenEntry);

        return new RespLoginDTO(authentication, user.getId());
    }

    @Override
    public void logout(User currentUser) {
        tokenRepository.deleteToken(currentUser.getId());
    }
}
