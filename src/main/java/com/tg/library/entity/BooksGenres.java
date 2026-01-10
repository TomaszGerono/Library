package com.tg.library.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "books_genres")
@IdClass(BooksGenresId.class)
public class BooksGenres implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "genre_id")
    private Long genreId;

    @Id
    @Column(name = "book_id")
    private Long bookId;

}
