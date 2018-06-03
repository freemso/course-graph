package java.rest;


import java.annotation.Authorization;
import java.annotation.CurrentUser;
import java.domain.User;
import java.dto.ReqLoginDTO;
import java.dto.RespLoginDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/token")
public interface TokenController {

    /**
     * User login request.
     * @param reqLoginDTO, required login form data
     * @return login response DTO
     */
    @PostMapping
    ResponseEntity<RespLoginDTO> login(@Valid @RequestBody ReqLoginDTO reqLoginDTO);

    /**
     * User logout request.
     * @param currentUser, authorized current user
     * @return response with empty body
     */
    @DeleteMapping
    @Authorization
    ResponseEntity logout(@CurrentUser User currentUser);
}
