package com.training.blinkist.repository;

import com.training.blinkist.modelentity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {

    @Modifying @Transactional
    @Query(value = "update Book b set b.isActive = false where b.bookId = :bookId")
    int disableBookByBookId(@Param("bookId") UUID bookId);

    @Query(value = "select b from Book b where b.numberOfReads > :minReadsForPopularBooks")
    List<Book> findPopularBooks(@Param("minReadsForPopularBooks") int minReadsForPopularBooks);

    @Query(value = "select b from Book b where b.creationDate >  current_date - (:minReadsForPopularBooks + 0) order by b.creationDate desc")
    List<Book> findRecentlyAddedBooks(@Param("minReadsForPopularBooks") int minReadsForPopularBooks);

    List<Book> findBookByGenreGenreId(UUID genreID);


}
