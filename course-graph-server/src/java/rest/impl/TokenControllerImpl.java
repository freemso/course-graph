package main.java.rest.impl;

import main.java.domain.User;
import main.java.dto.ReqLoginDTO;
import main.java.dto.RespLoginDTO;
import main.java.rest.TokenController;
import main.java.model.UserService;
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
