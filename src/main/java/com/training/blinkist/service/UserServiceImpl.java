package com.training.blinkist.service;


import com.training.blinkist.datatransferobject.UserDTO;
import com.training.blinkist.exceptions.InvalidRequestException;
import com.training.blinkist.exceptions.ResourceNotPresentException;
import com.training.blinkist.modelentity.User;
import com.training.blinkist.repository.UserRepository;
import com.training.blinkist.service.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper){
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    //Creates a new User
    @Override
    public UserDTO createNewUser(UserDTO userDTO) {
        if(userDTO == null) {
            throw new InvalidRequestException("User information is not provided.");
        }
        var user = modelMapper.map(userDTO, User.class);

        return modelMapper.map(userRepository.save(user), UserDTO.class);
    }

    //returns a list of all Users
    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        if(users.isEmpty()) {
            throw new ResourceNotPresentException("No users found.");
        }
        return users.stream().map(user -> modelMapper.map(user, UserDTO.class)).collect(Collectors.toList());
    }

    // returns User by UserName
    // throws Exception
    @Override
    public UserDTO findUserByUserName(String username) {
        Optional<User> optionalUser = userRepository.findByusername(username);

        if(optionalUser.isPresent()){
            return modelMapper.map(optionalUser.get(), UserDTO.class);
        }
        else{
            throw new ResourceNotPresentException(username + " not found.");
        }
    }

    //updates User Details
    //Throws Exception
    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        Optional<User> existingUser = userRepository.findById(userDTO.getUserId());

        User userUpdated;
        if(existingUser.isPresent())
        {
            userUpdated = existingUser.get();
        }
        else {
            throw new ResourceNotPresentException(userDTO.getUsername() + " User not found.");
        }

        userUpdated.setUserId(userDTO.getUserId());
        userUpdated.setUserId(userDTO.getUserId());
        userUpdated.setActive(userDTO.isActive());
        userUpdated.setAdmin(userDTO.isAdmin());
        userUpdated.setEmail(userDTO.getEmail());
        userUpdated.setName(userDTO.getName());
        userUpdated.setUsername(userDTO.getUsername());

        var updatedUser= userRepository.save(userUpdated);

        return (modelMapper.map(updatedUser, UserDTO.class));
    }
}

