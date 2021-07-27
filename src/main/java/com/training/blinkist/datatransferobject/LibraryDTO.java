package com.training.blinkist.datatransferobject;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class LibraryDTO {
    private LibraryIdDTO libraryIdDTO;
    private Date dateAdded;
    private boolean isCompleted;

}

