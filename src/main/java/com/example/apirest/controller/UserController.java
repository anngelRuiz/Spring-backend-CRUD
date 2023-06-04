package com.example.apirest.controller;

import com.example.apirest.entity.User;
import com.example.apirest.entity.UserDTO;
import com.example.apirest.exceptions.GlobalExceptionHandler;
import com.example.apirest.exceptions.UserCreationException;
import com.example.apirest.exceptions.UserNotFoundException;
import com.example.apirest.services.UserServiceIMPL.UserServiceIMPL;
import jakarta.validation.Valid;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin("http://localhost:3000")
public class UserController {

    private final UserServiceIMPL userServiceIMPL;

    private final GlobalExceptionHandler globalExceptionHandler;

    public UserController(UserServiceIMPL userServiceIMPL ,GlobalExceptionHandler globalExceptionHandler){
        this.userServiceIMPL = userServiceIMPL;
        this.globalExceptionHandler = globalExceptionHandler;
    }

    @GetMapping()
    public ResponseEntity<List<User>> getUsers(){
        List<User> users = userServiceIMPL.getUsers();

        if(users.isEmpty()){
            return ResponseEntity.noContent().cacheControl(CacheControl.noCache()).build();
        }

        return ResponseEntity.ok().cacheControl(CacheControl.noCache()).body(users);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @Valid User user, UriComponentsBuilder uriBuilder){
        try{
            User userCreated = userServiceIMPL.createUser(user);
            UserDTO userDTO = convertToUserDTO(userCreated);
            URI location = uriBuilder.path("/{id}").buildAndExpand(userCreated.getId()).toUri();
            return ResponseEntity.created(location).body(userDTO);
        }catch (UserCreationException ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(globalExceptionHandler.handlerUserCreationException(ex));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create user");
        }
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
        try {
            User userUpdated = userServiceIMPL.updateUser(user);
            UserDTO userDTO = convertToUserDTO(userUpdated);
            return ResponseEntity.ok(userDTO);
        } catch (UserNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update user");
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") Long id) {
        try {
            User user = userServiceIMPL.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(globalExceptionHandler.handlerUserNotFoundException(ex));
        } catch (NumberFormatException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(globalExceptionHandler.handlerNumberFormatException(ex));
        }
    }


    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id){
        if(!userServiceIMPL.existById(id)){
            throw new UserNotFoundException(id);
        }
        userServiceIMPL.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private UserDTO convertToUserDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setAge(user.getAge());
        userDTO.setGender(user.getGender());
        userDTO.setDepartment(user.getDeparment());
        return userDTO;
    }

//    private ResponseEntity<?> handlerUserException(Exception ex){
//        if(ex instanceof UserNotFoundException){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(globalExceptionHandler.handlerUserNotFoundException((UserNotFoundException) ex));
//        }else if(ex instanceof NumberFormatException){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(globalExceptionHandler.handlerNumberFormatException((NumberFormatException) ex));
//        }else{
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to get User");
//        }
//    }

//    Approach 2
//    @GetMapping(path = "/{id}")
//    public ResponseEntity<User> getUser2(@PathVariable("id") Long id) {
//        User user;
//        try {
//            user = userServiceIMPL.getUserById(id);
//        } catch (UserNotFoundException ex) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", ex);
//        } catch (NumberFormatException ex) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ID format", ex);
//        }
//
//        return ResponseEntity.ok(user);
//    }

    /*
    getUser approaches -
    Approach 1:
    It uses a try-catch block to handle exceptions and return the appropriate HTTP status.
    The response type is ResponseEntity<?>, allowing flexibility in returning different response types based on the exception.
    It relies on a global exception handler to handle the exceptions and return a custom response.

    Approach 2:
    It uses the ResponseStatusException to handle exceptions and return the appropriate HTTP status.
    The response type is explicitly defined as ResponseEntity<User>.
    It throws the exception directly, allowing it to be caught and handled by a global exception handler if available.
    * */

}
