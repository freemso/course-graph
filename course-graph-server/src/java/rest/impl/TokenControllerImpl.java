package java.rest.impl;

import java.domain.User;
import java.dto.ReqLoginDTO;
import java.dto.RespLoginDTO;
import java.rest.TokenController;
import java.model.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class TokenControllerImpl implements TokenController {

    private final UserService userService;

    @Autowired
    TokenControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<RespLoginDTO> login(ReqLoginDTO reqLoginDTO) {
        RespLoginDTO respLoginDTO = userService.login(
                reqLoginDTO.getEmail(), reqLoginDTO.getPassword());

        return new ResponseEntity<>(respLoginDTO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity logout(User currentUser) {
        userService.logout(currentUser);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
