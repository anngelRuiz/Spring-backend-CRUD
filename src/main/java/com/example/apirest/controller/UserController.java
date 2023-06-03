package com.example.apirest.controller;

import com.example.apirest.entity.User;
import com.example.apirest.exceptions.ErrorResponse;
import com.example.apirest.exceptions.GlobalExceptionHandler;
import com.example.apirest.exceptions.UserNotFoundException;
import com.example.apirest.services.UserServiceIMPL.UserServiceIMPL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin("http://localhost:3000")
public class UserController {

    @Autowired
    UserServiceIMPL userServiceIMPL;

    private final GlobalExceptionHandler globalExceptionHandler;

    public UserController(GlobalExceptionHandler globalExceptionHandler){
        this.globalExceptionHandler = globalExceptionHandler;
    }

    @GetMapping()
    public List<User> getUsers(){
        return userServiceIMPL.getUsers();
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        User userCreated = userServiceIMPL.createUser(user);
        return new ResponseEntity<>(userCreated, HttpStatus.CREATED);
        // return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
        User userUpdated = userServiceIMPL.updateUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userUpdated);
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
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        userServiceIMPL.DeleteUser(id);
        return ResponseEntity.ok().build();
    }

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
