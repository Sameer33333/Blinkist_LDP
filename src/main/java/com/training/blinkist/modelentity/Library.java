package com.training.blinkist.modelentity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

//Entity class that contains a record of books that were added to all subscriptions


@Entity
@Getter
@Setter
@Table(name = "library")
//@IdClass(LibraryId.class)
public class Library {

   public Library(){};


    public Library(LibraryId libraryId) {
        this.libraryId = libraryId;
        this.dateAdded = new Date();
    }

    @EmbeddedId
    private LibraryId libraryId;

    // date of addition of book to a user's personal library
    @CreationTimestamp
    @Column(name = "date_added")
    private Date dateAdded;

    //info on whether the user has completed reading this added book
    @Column(name = "is_completed")
    private boolean isCompleted;


}
