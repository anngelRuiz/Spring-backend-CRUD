package com.example.apirest.services;

import com.example.apirest.entity.User;
import com.example.apirest.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public List<User> getUsers();
    public User createUser(User user);
    public User updateUser(User user);
    public User getUserById(Long id);
    public void DeleteUser(Long id);
}
