package com.example.apirest.services;

import com.example.apirest.entity.User;
import com.example.apirest.exceptions.ResourceNotFoundException;

import java.util.List;

public interface UserService {

    public List<User> getUsers();
    public User createUser(User user);
    public User updateUser(User user);
    public User findUser(Long id) throws ResourceNotFoundException;
    public void DeleteUser(Long id);
}
