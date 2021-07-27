package com.training.blinkist.modelentity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


//represents Genre of a Book
@Entity
@Getter
@Setter
@Table(name = "genre")
public class Genre{
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "genre_id")
    private UUID genreId;

    @Column(name = "genre_name")
    private String genreName;

    @Column(name = "description")
    private String description;


    @OneToMany(mappedBy = "genre", fetch = FetchType.LAZY)
    private List<Book> bookList = new ArrayList<>();

}
