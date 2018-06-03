package java.model;

import java.domain.User;
import java.dto.RespLoginDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    RespLoginDTO login(String email, String password);

    void logout(User currentUser);


}
