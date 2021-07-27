package com.training.blinkist.controller;

import com.training.blinkist.datatransferobject.BookDTO;
import com.training.blinkist.datatransferobject.LibraryDTO;
import com.training.blinkist.datatransferobject.UserDTO;
import com.training.blinkist.exceptions.BookAlreadyExisitsInUserLibrary;
import com.training.blinkist.exceptions.InvalidRequestException;
import com.training.blinkist.exceptions.ResourceNotPresentException;
import com.training.blinkist.exceptions.UserAlreadyExisitsException;
import com.training.blinkist.service.interfaces.LibraryService;
import com.training.blinkist.service.interfaces.UserService;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final LibraryService libraryService;


    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper, LibraryService libraryService){
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.libraryService = libraryService;
    }

    @PostMapping("")
    @ApiOperation(value = "Add a new User", notes = "", response = UserDTO.class)
    public ResponseEntity<UserDTO> addNewUser(@RequestBody @Valid UserDTO userDTO) throws UserAlreadyExisitsException {
        //var user = modelMapper.map(userDTO, User.class);
        if (userDTO.getUserId() !=null) {
            UserDTO user = userService.findUserByUserName(userDTO.getUsername());
            if (user != null) {
                throw new UserAlreadyExisitsException("User already exists with user id " + userDTO.getUserId());
            }
        }
        var newUser = userService.createNewUser(userDTO);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }


    @PutMapping("/{user-id}")
    @ApiOperation(value = "Update User Details", notes = "user-id provided in url", response = UserDTO.class)
    public ResponseEntity<UserDTO> updateUser(@RequestBody @Valid UserDTO userDTO) throws InvalidRequestException {
        //var user = modelMapper.map(userDTO, User.class);
        var updatedUser = userService.updateUser(userDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @GetMapping("/{user-id}/user_name")
    @ApiOperation(value = "Get user by user name", notes = "provide username in path url", response = UserDTO.class)
    public ResponseEntity<UserDTO> findUserByUsername(@PathVariable(name="user_name") String userName){
        var user = userService.findUserByUserName(userName);

        if(user == null){
            throw new ResourceNotPresentException("User " + userName + " not found.");
        }
        return new ResponseEntity<>(modelMapper.map(user, UserDTO.class), HttpStatus.OK);

    }

    @GetMapping("")
    @ApiOperation(value = "Get all users", notes = "", response = UserDTO.class)
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<UserDTO> usersList = userService.getAllUsers();

        if(usersList.isEmpty()){
            throw new ResourceNotPresentException("No users found.");
        }

        return new ResponseEntity<>(usersList, HttpStatus.OK);
    }

    @PostMapping("/{user-id}/userLibrary/{book-id}")
    @ApiOperation(value = "Add new book to User Library", notes = "provide user-id in the url", response = String.class)
    public ResponseEntity<String> addNewBookToUserLibrary(@PathVariable(name= "user-id") UUID userId, @PathVariable(name= "book-id") UUID bookId) throws BookAlreadyExisitsInUserLibrary {
        if ((libraryService.getBookInUserLibrary(userId, bookId).isPresent()))
        {
            throw new BookAlreadyExisitsInUserLibrary("Book is already added in user library");
        }
        else {
            libraryService.addNewUserBook(bookId, userId);
            return new ResponseEntity<>("New user book has been added to user library.", HttpStatus.OK);
        }
    }

    @GetMapping("/{user-id}/userLibrary")
    @ApiOperation(value = "Get all books in user Library", notes = "provide user-id in the url", response = LibraryDTO.class)
    public ResponseEntity<List<BookDTO>> getAllUserBooks(@PathVariable(name = "user-id") UUID userId){

        List<BookDTO> userLibrary =  libraryService.getAllUserBooks(userId);

        return new ResponseEntity<>(userLibrary, HttpStatus.OK);
    }

}
