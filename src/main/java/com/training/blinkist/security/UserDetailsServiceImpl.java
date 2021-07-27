package com.training.blinkist.security;


import com.training.blinkist.exceptions.ResourceNotPresentException;
import com.training.blinkist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

    @Service
    public class UserDetailsServiceImpl implements UserDetailsService {

        @Autowired
        private UserRepository userRepository;

        @Override
        public UserDetails loadUserByUsername(String userName) throws ResourceNotPresentException{
            //Optional<User> user = userRepository.findByUserName(userName);
return null;
            //return user.map(UserDetailsImpl::new).orElseThrow(() -> new ResourceNotPresentException("User not found"));
        }
    }


