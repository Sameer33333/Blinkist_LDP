package com.training.blinkist.datatransferobject;

 import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.UUID;


@Getter
@Setter
public class BookDTO {

    private UUID bookId;
    private String bookTitle;
    private Date creationDate;
    private String author;
    private byte[] coverImage;
    @JsonProperty
    private boolean isActive;
    private UUID languageId;
    private UUID genreId;
    private int numberOfReads;


    private GenreDTO genreDTO;
    private LanguageDTO languageDTO;

}
