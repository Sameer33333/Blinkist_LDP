package com.training.blinkist.modelentity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

// Represents a composite ID to uniquely identify an identity in the Library Entity/Table

  @Embeddable
    @Getter
    @Setter
    @EqualsAndHashCode
    public class LibraryId implements Serializable {

      public static final long SERIALID =1L;

        @ManyToOne
        @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
        private User user;

        @ManyToOne
        @JoinColumn(name = "book_id", referencedColumnName = "book_id", nullable = false)
        private Book book;
    }
