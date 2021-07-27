package com.training.blinkist;


import com.training.blinkist.datatransferobject.BookDTO;
import com.training.blinkist.datatransferobject.GenreDTO;
import com.training.blinkist.datatransferobject.LanguageDTO;
import com.training.blinkist.exceptions.BookNotPresentException;
import com.training.blinkist.service.BookServiceImpl;
import com.training.blinkist.service.LibraryServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

    @SpringBootTest
    @ActiveProfiles("test")
    class BookServiceTest {

    @Autowired
    BookServiceImpl bookService;

    @Autowired
    LibraryServiceImpl libraryService;


    CreateTestArtifacts createTestData = new CreateTestArtifacts();

    @Test
    void saveBookTest() {
        GenreDTO genreDTO = libraryService.addNewGenre(createTestData.createNewGenre("test category", "This is a test category"));
        LanguageDTO languageDTO = libraryService.addNewLanguage(createTestData.addNewLanguage("us-en", "us English", true));


        BookDTO bookDTO = createTestData.createNewBook(genreDTO, languageDTO, "Space book", true, "Elon Musk", 200);
        var addedBook = bookService.saveBook(bookDTO);

        Assertions.assertNotNull(addedBook);
        var addedBooks = bookService.getAllBooks();
        Assertions.assertNotNull(addedBooks);

    }

    @Test
    void updateBookTest() {

        GenreDTO genreDTO = libraryService.addNewGenre(createTestData.createNewGenre("test category new", "This is a new test category"));
        LanguageDTO languageDTO = libraryService.addNewLanguage(createTestData.addNewLanguage("uk-en", "UK English", true));

        BookDTO bookDTO = createTestData.createNewBook(genreDTO, languageDTO, "Science book", true, "Newton", 200);

        var addedBook = bookService.saveBook(bookDTO);

        BookDTO book1 = null;
        try {
            book1 = bookService.getBookByBookId(addedBook);
        } catch (BookNotPresentException e) {
            e.printStackTrace();
        }

        bookDTO = book1;

        if (bookDTO != null)
            bookDTO.setBookTitle("updated Title");

        var updatedBook = bookService.updateBook(bookDTO);

        Assertions.assertEquals("updated Title", updatedBook.getBookTitle());

    }

    @Test
    void getBooksByCategory() {
        GenreDTO genreDTO = libraryService.addNewGenre(createTestData.createNewGenre("Science", "This is a science category"));
        LanguageDTO languageDTO = libraryService.addNewLanguage(createTestData.addNewLanguage("us-en", "us English", true));


        var bookDTO = createTestData.createNewBook(genreDTO, languageDTO, "Space book", true, "Elon Musk", 20);
        bookService.saveBook(bookDTO);

        genreDTO = libraryService.addNewGenre(createTestData.createNewGenre("space", "This is a space category"));

        bookDTO = createTestData.createNewBook(genreDTO, languageDTO, "science book", true, "Einstein", 20);
        bookService.saveBook(bookDTO);

        List<BookDTO> bookList = bookService.getBooksByGenre(genreDTO.getGenreId());

        Assertions.assertEquals(1, bookList.size());
        Assertions.assertEquals(genreDTO.getGenreId(), bookList.get(0).getGenreId());
    }

    @Test
    void getPopularBooksTest() {
        GenreDTO genreDTO = libraryService.addNewGenre(createTestData.createNewGenre("Science", "This is a science category"));
        LanguageDTO languageDTO = libraryService.addNewLanguage(createTestData.addNewLanguage("us-en", "us English", true));


        var bookDTO = createTestData.createNewBook(genreDTO, languageDTO, "Space book", true, "Elon Musk", 10);
        var addedBook = bookService.saveBook(bookDTO);

        genreDTO = libraryService.addNewGenre(createTestData.createNewGenre("space", "This is a space category"));

        bookDTO = createTestData.createNewBook(genreDTO, languageDTO, "book", true, "Einstein", 20);
        bookService.saveBook(bookDTO);

        List<BookDTO> bookList = bookService.getPopularBooks(19);

        Assertions.assertEquals(1, bookList.size(), 2);
    }

    @Test
    void getRecentlyAddedBooksTest() {
        GenreDTO genreDTO = libraryService.addNewGenre(createTestData.createNewGenre("Science", "This is a science category"));
        LanguageDTO languageDTO = libraryService.addNewLanguage(createTestData.addNewLanguage("us-en", "us English", true));


        var bookDTO = createTestData.createNewBook(genreDTO, languageDTO, "Space book", true, "Elon Musk", 10);
        var addedBook = bookService.saveBook(bookDTO);

        genreDTO = libraryService.addNewGenre(createTestData.createNewGenre("space", "This is a space category"));

        bookDTO = createTestData.createNewBook(genreDTO, languageDTO, "science book", true, "Einstein", 20);
        bookService.saveBook(bookDTO);

        List<BookDTO> bookList = bookService.getRecentlyAddedBooks(10);

        Assertions.assertEquals(2, bookList.size(), 8);
    }

    @Test
    void getBookByBookId() {

        GenreDTO genreDTO = libraryService.addNewGenre(createTestData.createNewGenre("Science", "This is a science category"));
        LanguageDTO languageDTO = libraryService.addNewLanguage(createTestData.addNewLanguage("us-en", "us English", true));


        var bookDTO = createTestData.createNewBook(genreDTO, languageDTO, "Space book", true, "Elon Musk", 10);
        var addedBook = bookService.saveBook(bookDTO);

        BookDTO addedBookDTO = null;
        try {
            addedBookDTO = bookService.getBookByBookId(addedBook);
        } catch (BookNotPresentException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(addedBook, addedBookDTO.getBookId());

    }

    @Test
    void updateBookTestBookNotPresent() {

        GenreDTO genreDTO = libraryService.addNewGenre(createTestData.createNewGenre("test category new", "This is a new test category"));
        LanguageDTO languageDTO = libraryService.addNewLanguage(createTestData.addNewLanguage("uk-en", "UK English", true));

        BookDTO bookDTO = createTestData.createNewBook(genreDTO, languageDTO, "Science book", true, "Newton", 200);

        var addedBook = bookService.saveBook(bookDTO);
        bookService.deleteBookByBookId(addedBook);
        Assertions.assertThrows(Exception.class, () -> {
            bookService.getBookByBookId(addedBook);
        });


    }
}
