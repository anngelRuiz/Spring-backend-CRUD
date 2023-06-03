package com.example.apirest.services.UserServiceIMPL;

import com.example.apirest.entity.User;
import com.example.apirest.exceptions.ResourceNotFoundException;
import com.example.apirest.repository.UserRepository;
import com.example.apirest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceIMPL implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getUsers() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

//    @Override
//    public User findUser(Long id) {
//        return userRepository.findById(id);
//    }

//    @Override
//    public User findUser(Long id) throws ResourceNotFoundException {
//        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
//    }

    @Override
    public User findUser(Long id) throws ResourceNotFoundException {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new ResourceNotFoundException("User not found with id " + id);
        }
    }

    @Override
    public void DeleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
