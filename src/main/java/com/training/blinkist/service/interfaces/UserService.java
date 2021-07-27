package com.training.blinkist.service.interfaces;


import com.training.blinkist.datatransferobject.UserDTO;
import com.training.blinkist.exceptions.InvalidRequestException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    UserDTO createNewUser(UserDTO userDTO);

    //Method to return list of all the users.
    List<UserDTO> getAllUsers();

    //Method to return specific user.
    UserDTO findUserByUserName(String username);

    //Method to update an user.
    UserDTO updateUser(UserDTO userDTO) throws InvalidRequestException;

}