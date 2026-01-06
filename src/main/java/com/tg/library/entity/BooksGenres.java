package com.tg.library.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@Table(name = "books_genres")
public class BooksGenres implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "genre_id")
    private Long genreId;

    @Id
    @Column(name = "book_id")
    private Long bookId;

}
