package com.training.blinkist.datatransferobject;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class LibraryIdDTO {

    private UUID bookId;
    private UUID userId;

    private BookDTO bookDTO;
    private UserDTO userDTO;
}
