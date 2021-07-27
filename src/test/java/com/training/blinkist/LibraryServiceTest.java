package com.training.blinkist;


import com.training.blinkist.datatransferobject.BookDTO;
import com.training.blinkist.datatransferobject.GenreDTO;
import com.training.blinkist.datatransferobject.LanguageDTO;
import com.training.blinkist.datatransferobject.UserDTO;
import com.training.blinkist.modelentity.Book;
import com.training.blinkist.modelentity.Library;
import com.training.blinkist.modelentity.LibraryId;
import com.training.blinkist.modelentity.User;
import com.training.blinkist.service.interfaces.BookService;
import com.training.blinkist.service.interfaces.LibraryService;
import com.training.blinkist.service.interfaces.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.List;

@SpringBootTest
    @ActiveProfiles("test")
    class LibraryServiceTest {

        @Autowired
        LibraryService libraryService;

        @Autowired
        UserService userService;

        @Autowired
        BookService bookService;

        ModelMapper modelMapper =new ModelMapper();

        CreateTestArtifacts createTestData = new CreateTestArtifacts();

        @Test
        void addCategoryTest(){
            GenreDTO genreDTO = libraryService.addNewGenre(createTestData.createNewGenre("test category", "This is a test category"));

            genreDTO = libraryService.addNewGenre(genreDTO);

            Assertions.assertNotNull(genreDTO);
        }

        @Test
        void addNewLanguageTest(){
            LanguageDTO languageDTO = libraryService.addNewLanguage(createTestData.addNewLanguage("us-en", "us English", true));

            languageDTO = libraryService.addNewLanguage(languageDTO);

            Assertions.assertNotNull(languageDTO);
        }

        @Test
        void addNewUserBookTest(){
            GenreDTO genreDTO = libraryService.addNewGenre(createTestData.createNewGenre("test category", "This is a test category"));
            genreDTO = libraryService.addNewGenre(genreDTO);
            LanguageDTO languageDTO = libraryService.addNewLanguage(createTestData.addNewLanguage("us-en", "us English", true));
            languageDTO = libraryService.addNewLanguage(languageDTO);

            UserDTO userDTO = userService.createNewUser(createTestData.createNewUserData("test123", "testuser", "test123@gmail.com", 98765433210L, true, true));
              var addedUser = userService.createNewUser(userDTO);
            BookDTO bookDTO = createTestData.createNewBook(genreDTO, languageDTO, "Space book", true, "Elon Musk", 200);
            var addedBook = bookService.saveBook(bookDTO);

            Library userLibrary = new Library();
            LibraryId userLibraryId = new LibraryId();

           userLibraryId.setUser(modelMapper.map(userDTO, User.class));
            userLibraryId.setBook(modelMapper.map(bookDTO, Book.class));


            userLibrary.setLibraryId(userLibraryId);
            userLibrary.setCompleted(false);
            userLibrary.setDateAdded(new Date());


            libraryService.addNewUserBook(addedBook , addedUser.getUserId());

            List<BookDTO> userLibraryList = libraryService.getAllUserBooks(userDTO.getUserId());

            Assertions.assertEquals(1, userLibraryList.size());
            var book = libraryService.getBookInUserLibrary(addedUser.getUserId(), addedBook);
            Assertions.assertEquals(book.isPresent(),true);


        }
    }

