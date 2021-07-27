package com.training.blinkist.service;

import com.training.blinkist.datatransferobject.BookDTO;
import com.training.blinkist.datatransferobject.GenreDTO;
import com.training.blinkist.datatransferobject.LanguageDTO;
import com.training.blinkist.exceptions.BookNotPresentException;
import com.training.blinkist.exceptions.ResourceNotPresentException;
import com.training.blinkist.modelentity.Book;
import com.training.blinkist.repository.BookRepository;
import com.training.blinkist.repository.GenreRepository;
import com.training.blinkist.repository.LanguageRepository;
import com.training.blinkist.service.interfaces.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;
    private final GenreRepository genreRepository;
    private final LanguageRepository languageRepository;


    @Autowired
    public BookServiceImpl(BookRepository bookRepository, ModelMapper modelMapper, GenreRepository genreRepository,LanguageRepository languageRepository){
        this.modelMapper = modelMapper;
        this.bookRepository = bookRepository;
        this.genreRepository=genreRepository;
        this.languageRepository= languageRepository;
    }

    @Override
    public List<BookDTO> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        if(books.isEmpty()) {
            throw new ResourceNotPresentException("No books found.");
        }
        return books.stream().map(book -> modelMapper.map(book, BookDTO.class)).collect(Collectors.toList());
    }

    @Override
    public UUID saveBook(BookDTO bookDTO) {

        var book = modelMapper.map(bookDTO, Book.class);
        var genre = genreRepository.findById(bookDTO.getGenreId());
        var language = languageRepository.findById(bookDTO.getLanguageId());

        if(genre.isPresent()) {


            book.setGenre(genre.get());
        }
        if(language.isPresent())
        {
            book.setLanguage(language.get());
        }



        var addedBook = bookRepository.save(book);
        return addedBook.getBookId();
    }

    @Override
    public BookDTO updateBook(BookDTO bookDTO) {

        var book = modelMapper.map(bookDTO, Book.class);
        var updatedBook = bookRepository.save(book);
        return modelMapper.map(updatedBook, BookDTO.class);
    }

    @Override
    public List<BookDTO> getBooksByGenre(UUID genreID) {

        List<Book> books = bookRepository.findBookByGenreGenreId(genreID);
        return books.stream().map(book -> modelMapper.map(book, BookDTO.class)).collect(Collectors.toList());
    }


    @Override
    public BookDTO getBookByBookId(UUID bookId) throws BookNotPresentException {

        GenreDTO genreDTO;
        LanguageDTO languageDTO;
        Optional<Book> optionalBook = bookRepository.findById(bookId);
          if(optionalBook.isPresent())
          {
              var genre = optionalBook.get().getGenre();
              var language = optionalBook.get().getLanguage();
              genreDTO = modelMapper.map(genre, GenreDTO.class);
              languageDTO= modelMapper.map(language, LanguageDTO.class);
          }
          else{
           throw new BookNotPresentException("book not present");
          }

            var bookDTO = modelMapper.map(optionalBook.orElseGet(optionalBook::orElseThrow), BookDTO.class );
            bookDTO.setGenreDTO(genreDTO);
            bookDTO.setLanguageDTO(languageDTO);
            return bookDTO;

        }



    @Override
    public void deleteBookByBookId(UUID bookId) {
        bookRepository.deleteById(bookId);
    }

    @Override
    public List<BookDTO> getPopularBooks(int minReadsForPopularBooks) {
        List<Book> books =bookRepository.findPopularBooks(minReadsForPopularBooks);
        return books.stream().map(book -> modelMapper.map(book, BookDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> getRecentlyAddedBooks(int numOfDaysForRecentlyAdded) {
        List<Book> books= bookRepository.findRecentlyAddedBooks(numOfDaysForRecentlyAdded);
        return books.stream().map(book -> modelMapper.map(book, BookDTO.class)).collect(Collectors.toList());
    }


}
