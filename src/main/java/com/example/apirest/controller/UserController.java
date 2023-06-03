package com.example.apirest.controller;

import com.example.apirest.entity.User;
import com.example.apirest.exceptions.ResourceNotFoundException;
import com.example.apirest.services.UserServiceIMPL.UserServiceIMPL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@CrossOrigin("http://localhost:3000")
public class UserController {

    @Autowired
    UserServiceIMPL userServiceIMPL;

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
    public User findUserById(@PathVariable("id") Long id) {
        return userServiceIMPL.findUser(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    }

    @GetMapping(path = "/{id}")
    public User getUserById(@PathVariable Long id){
        return userServiceIMPL.fin
    }

//    @GetMapping(path = "/{id}")
//    public User findUserById(@PathVariable("id") Long id) {
//        return userServiceIMPL.findUser(id);
//    }



//    @GetMapping(path = "/{id}")
//    public ResponseEntity<?> findUserById(@PathVariable("id") Long id) {
//        User user = userServiceIMPL.findUser(id);
//        return ResponseEntity.ok(user);
//    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        userServiceIMPL.DeleteUser(id);
        return ResponseEntity.ok().build();

    }

}
