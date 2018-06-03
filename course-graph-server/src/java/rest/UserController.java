package java.rest;


import java.annotation.Authorization;
import java.annotation.CurrentUser;
import java.domain.User;
import java.dto.ReqRegisterDTO;
import java.dto.ReqUpdateUserDTO;
import java.dto.RespUserPublicDTO;
import java.dto.RespUserPrivateDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public interface UserController {

    /**
     * User registration request.
     * @param reqRegisterDTO, required register form data
     * @return user private DTO with email field
     */
    @PostMapping
    ResponseEntity<RespUserPrivateDTO> register(
            @Valid @RequestBody ReqRegisterDTO reqRegisterDTO);

    /**
     * Get all users in a list.
     * @return a list of user public DTO
     */
    @GetMapping
    ResponseEntity<List<RespUserPublicDTO>> getAllUsers();

    /**
     * Get meta data of the user with id equals to {uid}.
     * Both authorized and unauthorized requests are acceptable.
     * Only authorized currentUser with the same id as {uid} could get
     * user private data. Others get public data.
     * @param uid, id of the user to get
     * @param currentUser, nullable current authorized user
     * @return user public/private DTO
     */
    @GetMapping("/{uid}")
    ResponseEntity<RespUserPublicDTO> getUser(@PathVariable long uid, @CurrentUser User currentUser);

    /**
     * Update user meta data.
     * @param uid, id of the user to be updated
     * @param currentUser, current login user
     * @param reqUpdateUserDTO, data fields to update
     * @return updated user private DTO
     */
    @PutMapping("/{uid}")
    @Authorization
    ResponseEntity<RespUserPrivateDTO> updateUser(@PathVariable long uid,
                                                 @CurrentUser User currentUser,
                                                 @RequestBody ReqUpdateUserDTO reqUpdateUserDTO);

    /**
     * Delete the user with {uid}
     * @param uid, id of the user to be deleted
     * @param currentUser, current login user
     * @return empty response body
     */
    @DeleteMapping("/{uid}")
    @Authorization
    ResponseEntity deleteUser(@PathVariable long uid, @CurrentUser User currentUser);
}
