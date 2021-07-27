package com.training.blinkist.controller;


import com.training.blinkist.datatransferobject.BookDTO;
import com.training.blinkist.datatransferobject.GenreDTO;
import com.training.blinkist.datatransferobject.LanguageDTO;
import com.training.blinkist.exceptions.BookNotPresentException;
import com.training.blinkist.service.interfaces.BookService;
import com.training.blinkist.service.interfaces.LibraryService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/books")
@Slf4j
public class BookController {


    private static final int NUM_OF_DAYS_FOR_RECENTLY_ADDED = 14;


    private static final int MIN_READS_FOR_POPULAR_BOOKS = 100;

    private final BookService bookService;
    private final LibraryService libraryService;


    @Autowired
    public BookController(BookService bookService, ModelMapper modelMapper,LibraryService libraryService ){

        this.bookService = bookService;
        this.libraryService= libraryService;

    }


    @PostMapping("")
    @ApiOperation(value = "Add a new book", notes = "Adds a new book", response = BookDTO.class)
    public ResponseEntity<String> addNewBook(@RequestBody BookDTO bookDTO){
        bookService.saveBook(bookDTO);
        return new ResponseEntity<>("New book has been added.", HttpStatus.OK);
    }

    @GetMapping("")
    @ApiOperation(value = "Get books by parameter", notes = "add Param to url", response = BookDTO.class)
    public ResponseEntity<List<BookDTO>> getAllBooks(@RequestParam Map<String, String> params)
    {
        List<BookDTO> booksDTO;
        if(params.get("getPopularBooks") != null && Boolean.parseBoolean(params.get("getPopularBooks")))
        {
            booksDTO = bookService.getPopularBooks(MIN_READS_FOR_POPULAR_BOOKS);
        }
        else if(params.get("getRecentlyAddedBooks") != null && Boolean.parseBoolean(params.get("getRecentlyAddedBooks")))
        {
            booksDTO = bookService.getRecentlyAddedBooks(NUM_OF_DAYS_FOR_RECENTLY_ADDED);
        }
        else if(params.get("getBooksByGenreId") != null)
        {
            booksDTO = bookService.getBooksByGenre(UUID.fromString(params.get("getBooksByGenreId")));
        }
        else
        {
            booksDTO = bookService.getAllBooks();
        }

        return new ResponseEntity<>(booksDTO, HttpStatus.OK);

    }

    @PutMapping("/{book-id}")
    @ApiOperation(value = "Update the book", notes = "book-id in the url", response = BookDTO.class)
    public ResponseEntity<BookDTO> updateBook(@RequestBody BookDTO bookDTO){

        var updateBookDTO = bookService.updateBook(bookDTO);
        return new ResponseEntity<>(updateBookDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{book-id}")
    @ApiOperation(value = "Delete book", notes = "book name in the url", response = BookDTO.class)
    public boolean deleteBookByBookId(@PathVariable(value = "book-id") UUID bookId){
        bookService.deleteBookByBookId(bookId);
        return true;
    }

    @GetMapping("/{book-id}")
    @ApiOperation(value = "Get book by book-id", notes = "provide book-id in the url", response = BookDTO.class)
    public ResponseEntity<BookDTO> getBookByBookId(@PathVariable("book-id") UUID bookId){
        BookDTO bookDTO = null;
        try {
            bookDTO = bookService.getBookByBookId(bookId);
        } catch (BookNotPresentException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(bookDTO, HttpStatus.OK);

    }

    @PostMapping("/genre")
    @ApiOperation(value = "Add a new genre", notes = "", response = GenreDTO.class)
    public ResponseEntity<GenreDTO> addNewCategory(@RequestBody GenreDTO categoryDto){

        categoryDto = libraryService.addNewGenre(categoryDto);
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

    @PostMapping("/language")
    @ApiOperation(value = "Add a new language", notes = "", response = LanguageDTO.class)
    public ResponseEntity<LanguageDTO> addNewLanguage(@RequestBody LanguageDTO languageDto){

       var languageDto1 = libraryService.addNewLanguage(languageDto);
        return new ResponseEntity<>(languageDto1, HttpStatus.OK);
    }
}


