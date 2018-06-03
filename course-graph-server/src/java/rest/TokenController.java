package java.rest;


import java.annotation.Authorization;
import java.annotation.CurrentUser;
import java.domain.User;
import java.dto.ReqLoginDTO;
import java.dto.RespLoginDTO;
import java.model.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/token")
public class TokenController {

    private final UserService userService;

    @Autowired
    TokenController(UserService userService) {
        this.userService = userService;
    }

    /**
     * User login request.
     * @param reqLoginDTO, required login form data
     * @return login response DTO
     */
    @PostMapping
    ResponseEntity<RespLoginDTO> login(@Valid @RequestBody ReqLoginDTO reqLoginDTO) {
        RespLoginDTO respLoginDTO = userService.login(
                reqLoginDTO.getEmail(), reqLoginDTO.getPassword());

        return new ResponseEntity<>(respLoginDTO, HttpStatus.CREATED);
    }

    /**
     * User logout request.
     * @param currentUser, authorized current user
     * @return response with empty body
     */
    @DeleteMapping
    @Authorization
    ResponseEntity logout(@CurrentUser User currentUser) {
        userService.logout(currentUser);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
