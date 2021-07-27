package com.training.blinkist;

import com.training.blinkist.datatransferobject.BookDTO;
import com.training.blinkist.datatransferobject.GenreDTO;
import com.training.blinkist.datatransferobject.LanguageDTO;
import com.training.blinkist.datatransferobject.UserDTO;
import com.training.blinkist.modelentity.Book;
import com.training.blinkist.modelentity.Genre;
import com.training.blinkist.modelentity.Language;
import com.training.blinkist.modelentity.User;
import org.modelmapper.ModelMapper;


public class CreateTestArtifacts{

        ModelMapper modelMapper = new ModelMapper();

        public GenreDTO createNewGenre(String genreName, String description){
            Genre genre = new Genre();
            genre.setGenreName(genreName);
            genre.setDescription(description);

            return modelMapper.map(genre, GenreDTO.class);
        }

        public LanguageDTO addNewLanguage(String languageCode, String languageName, boolean isActive){
            Language language = new Language();
            language.setLanguageCode(languageCode);
            language.setLanguageName(languageName);
            //language.setActive(isActive);

            return modelMapper.map(language, LanguageDTO.class);
        }

        public BookDTO createNewBook(GenreDTO genreDTO, LanguageDTO languageDTO, String bookTitle, boolean isActive, String author, int numberOfReads){
            var bookDTO = new BookDTO();
            var book =new Book();

            bookDTO.setGenreId(genreDTO.getGenreId());
            bookDTO.setLanguageId(languageDTO.getLanguageId());
            bookDTO.setBookTitle(bookTitle);
            bookDTO.setActive(isActive);
            bookDTO.setAuthor(author);
            bookDTO.setNumberOfReads(numberOfReads);
            bookDTO.setGenreDTO(genreDTO);
            bookDTO.setLanguageDTO(languageDTO);


            return bookDTO;
        }

        public UserDTO createNewUserData(String username, String name, String email, long phone, boolean isActive, boolean isAdmin){
            User user = new User();

            user.setUsername(username);
            user.setName(name);
            user.setEmail(email);
            user.setPhone(phone);
            user.setAdmin(isAdmin);
            user.setActive(isActive);
            user.setPassword("33af75d2-b042-43e8-b772-84bb2fdd5b2c");

            return modelMapper.map(user, UserDTO.class);
        }
    }

