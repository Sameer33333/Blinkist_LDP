package com.training.blinkist.service.interfaces;

import com.training.blinkist.datatransferobject.BookDTO;
import com.training.blinkist.datatransferobject.GenreDTO;
import com.training.blinkist.datatransferobject.LanguageDTO;
import com.training.blinkist.modelentity.Library;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface LibraryService {


    Optional<Library> getBookInUserLibrary(UUID userId, UUID bookId) ;

    void addNewUserBook(UUID bookId, UUID userId);

    List<BookDTO> getAllUserBooks(UUID userId);

    GenreDTO addNewGenre(GenreDTO genreDTO);

    LanguageDTO addNewLanguage(LanguageDTO language);
}

