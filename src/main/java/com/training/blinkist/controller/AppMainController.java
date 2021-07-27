package com.training.blinkist.controller;

import com.training.blinkist.datatransferobject.UserDTO;
import com.training.blinkist.modelentity.User;
import com.training.blinkist.service.interfaces.UserService;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class AppMainController {


    private final UserService userService;
    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AppMainController(UserService userService, ModelMapper modelMapper, PasswordEncoder passwordEncoder){
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping("/registration")
    @ApiOperation(value = "Register a User", notes = "", response = String.class)
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO){
        var user = modelMapper.map(userDTO, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.createNewUser(userDTO);
        return new ResponseEntity<>("User is created successfully.", HttpStatus.CREATED);
    }

   // login

    //logout

//session creation

}
