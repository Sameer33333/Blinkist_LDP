package com.training.blinkist.datatransferobject;


 import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class LanguageDTO {
    private UUID languageId;
    private String languageName;
    private String languageCode;

}
