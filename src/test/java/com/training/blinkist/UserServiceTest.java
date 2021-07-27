package com.training.blinkist;

import com.training.blinkist.datatransferobject.UserDTO;
import com.training.blinkist.exceptions.ResourceNotPresentException;
import com.training.blinkist.modelentity.User;
import com.training.blinkist.repository.UserRepository;
import com.training.blinkist.service.UserServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
    @SpringBootTest
    @ActiveProfiles("test")
    class UserServiceTest {

    @Autowired
    ModelMapper modelMapper;


        @InjectMocks
        @Autowired
        UserServiceImpl userServiceImpl;

        @MockBean
        private UserRepository userRepository;


       //


        @BeforeEach
        void setup()
        {
            MockitoAnnotations.openMocks(this);
        }

        @AfterEach
        void tearDown()
        {

        }


        public User mockUser(String username, String password, boolean isAdmin, String name, long phone, String email, boolean isActive){
            User user = new User();

            user.setUsername(username);
            user.setPassword(password);
            user.setAdmin(isAdmin);
            user.setName(name);
            user.setPhone(phone);
            user.setEmail(email);
            user.setActive(isActive);

            return user;
        }

        @Test

        void createUserTest(){
            User newUser = mockUser("test_user1", "test_password1", true, "test1", 970519769, "test1@gmail.com", true);
           modelMapper =new ModelMapper();
            when( userRepository.save(any()) ).thenReturn(newUser);
            UserDTO newUserDTO = modelMapper.map(newUser, UserDTO.class);
            UserDTO createdUser = userServiceImpl.createNewUser(newUserDTO);
            Assertions.assertNotNull(createdUser);
        }

        @Test

        void getAllUsersTest(){
            User newUser = mockUser("test_user2", "test_password2", true, "test2", 22222222, "test2@gmail.com", true);
            //userServiceImpl.createNewUser(newUser);
            User newUser1 = mockUser("test_user3", "test_password3", true, "test3", 333333333, "test3@gmail.com", true);
            //userService.createNewUser(newUser);
            List<User> mockedList = new ArrayList<>();
            mockedList.add(newUser);
            mockedList.add(newUser1);
            when(userRepository.findAll()).thenReturn(mockedList);
            List<UserDTO> usersList = userServiceImpl.getAllUsers();

            Assertions.assertEquals(2, usersList.size());
        }

    @Test
    void getAllUsersFailTest(){
        when(userRepository.findAll()).thenReturn(Collections.emptyList());
        Assertions.assertThrows(ResourceNotPresentException.class, () -> {
            userServiceImpl.getAllUsers();});

    }

        @Test

        void getUserByUsernameTest(){
            User newUser = mockUser("test_user4", "test_password4", true, "test4", 4444444, "test4@gmail.com", true);
            //userService.createNewUser(newUser);
            when(userRepository.findByusername("test_user4")).thenReturn(java.util.Optional.ofNullable(newUser));
            //UserDTO newUserDTO = modelMapper.map(newUser, UserDTO.class);
            UserDTO user = userServiceImpl.findUserByUserName(newUser.getUsername());

            Assertions.assertEquals("test_user4", user.getUsername());
        }

    @Test
    void getUserByUsernameTestFail(){
        User newUser = mockUser("test_user4", "test_password4", true, "test4", 4444444, "test4@gmail.com", true);
        //userServiceImpl.createNewUser(modelMapper.map(newUser, UserDTO.class));
        when(userRepository.findByusername("test_user4")).thenReturn(java.util.Optional.ofNullable(newUser));
       // UserDTO newUserDTO = modelMapper.map(newUser, UserDTO.class);

        Assertions.assertThrows(ResourceNotPresentException.class, () -> {
                    userServiceImpl.findUserByUserName("wrongUser");});
    }
        //Assertions.assertEquals("test_user4", user.getUsername());




       @Test

        void updateUserTest(){
            User newUser = mockUser("test_user5", "test_password5", true, "test", 970519769, "test5@gmail.com", true);
            //var newUserDTO = modelMapper.map(newUser, UserDTO.class);
           // userServiceImpl.createNewUser(newUserDTO);
           when(userRepository.findByusername(any())).thenReturn(java.util.Optional.ofNullable(newUser));

             UserDTO newUserDTO = userServiceImpl.findUserByUserName(newUser.getUsername());

            //newUserDTO.setName("NameUpdated");

           newUserDTO.setActive(false);

           newUserDTO.setName("NameUpdated");

           when(userRepository.findById(any())).thenReturn(java.util.Optional.ofNullable(newUser));
           when(userRepository.save(any())).thenReturn(java.util.Optional.ofNullable(newUser));
           var updatedUser = userServiceImpl.updateUser(newUserDTO);

            Assertions.assertEquals("NameUpdated", updatedUser.getName());
           Assertions.assertFalse(updatedUser.isActive());
        }


        @Test

        void updateUserExceptionTest(){
            User newUser = mockUser("test_user6", "test_password6", true, "test6", 66666666, "test12@gmail.com", true);
            userServiceImpl.createNewUser(modelMapper.map(newUser, UserDTO.class));

            UserDTO newUserDTO = userServiceImpl.findUserByUserName(newUser.getUsername());

            newUserDTO.setUsername("");
            UserDTO updatedUser = newUserDTO;
            Assertions.assertThrows(Exception.class, () -> {
                userServiceImpl.updateUser(updatedUser);
            });
        }

        @Test()

        void getUserByUsernameExceptionTest(){
            User newUser = mockUser("test_user7", "test_password7", true, "test7", 77777777, "test7@gmail.com", true);
            when(userRepository.findByusername(any())).thenReturn(java.util.Optional.ofNullable(null));
            //userServiceImpl.createNewUser(newUser);
            Assertions.assertThrows(ResourceNotPresentException.class, () -> {
                userServiceImpl.findUserByUserName("wrongUser");
            });
        }
    }

