package com.training.blinkist.service.interfaces;



        import com.training.blinkist.datatransferobject.BookDTO;
        import com.training.blinkist.exceptions.BookNotPresentException;
        import org.springframework.stereotype.Service;

        import java.util.List;
import java.util.UUID;

@Service
public interface BookService
{

    UUID saveBook(BookDTO bookDTO);

    BookDTO updateBook(BookDTO bookDTO);

    List<BookDTO> getBooksByGenre(UUID genreID);

    BookDTO getBookByBookId(UUID bookId) throws BookNotPresentException;

    void deleteBookByBookId(UUID bookId);

    List<BookDTO> getPopularBooks(int popularBooks);

    List<BookDTO> getRecentlyAddedBooks(int recentlyAddedBooks);

    List<BookDTO> getAllBooks();
}