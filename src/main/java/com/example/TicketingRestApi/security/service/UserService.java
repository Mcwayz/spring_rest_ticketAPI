package com.example.TicketingRestApi.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TicketingRestApi.security.model.User;
import com.example.TicketingRestApi.security.repository.UserRepository;

@Service
public class UserService {

    // @Autowired
    // private BCryptPasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    //Get All Users
    public List<User> findAll() {
        return userRepository.findAll();
    }

      //Get User By Id
    public User findById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    //Delete User
    public void delete(int id) {
        userRepository.deleteById(id);
    }



    //save new user
    public void save(User user)
    {
        //encode password
        // user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }
    
}
