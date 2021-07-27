package com.training.blinkist.service;


import com.training.blinkist.datatransferobject.*;
import com.training.blinkist.modelentity.*;
import com.training.blinkist.repository.*;
import com.training.blinkist.service.interfaces.LibraryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LibraryServiceImpl implements LibraryService {


    private LibraryRepository libraryRepository;
    private GenreRepository genreRepository;
    private LanguageRepository languageRepository;
    private ModelMapper modelMapper;
    private BookRepository bookRepository;
    private UserRepository userRepository;

    @Autowired
    public LibraryServiceImpl(LibraryRepository libraryRepository, GenreRepository genreRepository, LanguageRepository languageRepository, BookRepository bookRepository, UserRepository userRepository, ModelMapper modelMapper) {

        this.libraryRepository = libraryRepository;
        this.genreRepository = genreRepository;
        this.languageRepository = languageRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    //gets a book in User Library
    public Optional<Library> getBookInUserLibrary(UUID userId, UUID bookId) {


        var libraryId = new LibraryId();
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        Optional<User> userOptional = userRepository.findById(userId);
        if (bookOptional.isPresent() && userOptional.isPresent()) {
            libraryId.setUser(userOptional.get());
            libraryId.setBook(bookOptional.get());


        }
        return libraryRepository.findById(libraryId);

    }


    // Adds a book to User Library
    @Override
    public void addNewUserBook(UUID bookId, UUID userId) {

        Optional<Book> bookOptional = bookRepository.findById(bookId);
        Optional<User> userOptional = userRepository.findById(userId);
        if (bookOptional.isPresent() && userOptional.isPresent()) {

            var libraryId = new LibraryId();
            libraryId.setUser(userOptional.get());
            libraryId.setBook(bookOptional.get());
            var newLibrary = new Library(libraryId);
            libraryRepository.save(newLibrary);
        }

    }

    // return a list of all user books
    @Override
    public List<BookDTO> getAllUserBooks(UUID userId){

        var userLibrary = libraryRepository.getAllUserBooks(userId);

        List<BookDTO> userBooksDTO = new ArrayList<>();
        for (Library l : userLibrary
        ) {

            var bookDTO = modelMapper.map(l.getLibraryId().getBook(), BookDTO.class);

            userBooksDTO.add(bookDTO);

        }
        return userBooksDTO;


    }



    // Adds a new Genre
        @Override
        public GenreDTO addNewGenre(GenreDTO genreDTO) {
            var genre = modelMapper.map(genreDTO, Genre.class);

            return modelMapper.map(genreRepository.save(genre), GenreDTO.class);
        }

        //Adds a new Language
        @Override
        public LanguageDTO addNewLanguage(LanguageDTO languageDTO) {
            var language = modelMapper.map(languageDTO, Language.class);

            return modelMapper.map(languageRepository.save(language), LanguageDTO.class);
        }
    }





