package com.example.TicketingRestApi.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.TicketingRestApi.security.model.User;
import com.example.TicketingRestApi.security.model.UserPrinciple;
import com.example.TicketingRestApi.security.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub

        User user = userRepository.findByUsername(username);

        if (user == null)
        {
            throw new UsernameNotFoundException("User not found!");
        }

        return new UserPrinciple(user);
    }
    
}
