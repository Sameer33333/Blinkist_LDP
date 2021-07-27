package com.training.blinkist.datatransferobject;

 import lombok.Getter;
 import lombok.Setter;
 import java.util.UUID;

@Getter
@Setter
public class GenreDTO{

    private UUID genreId;
    private String genreName;
    private String description;

}
