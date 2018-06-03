package java.model;

import java.config.ResponseExceptions.EmailOrPasswordException;
import java.domain.TokenEntry;
import java.domain.User;
import java.dto.RespLoginDTO;
import java.repository.TokenRepository;
import java.repository.UserRepository;

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

    public void logout(User currentUser) {
        tokenRepository.deleteToken(currentUser.getId());
    }
}
