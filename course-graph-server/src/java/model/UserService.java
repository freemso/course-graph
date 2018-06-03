package main.java.model;

import main.java.domain.User;
import main.java.dto.RespLoginDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    RespLoginDTO login(String email, String password);

    void logout(User currentUser);


}
