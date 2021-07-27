package com.training.blinkist.repository;

import com.training.blinkist.modelentity.Library;
import com.training.blinkist.modelentity.LibraryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface LibraryRepository extends JpaRepository<Library, LibraryId>
{

    @Query(value = "select l from Library l where l.libraryId.user.userId = :userId")
    List<Library> getAllUserBooks(@Param("userId") UUID userId);

    List<Library> findByLibraryIdUserUserId(UUID userId);


}