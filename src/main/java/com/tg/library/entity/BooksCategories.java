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
@Table(name = "books_categories")
@IdClass(BooksCategoriesId.class)
public class BooksCategories implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "category_id")
    private Long categoryId;

    @Id
    @Column(name = "book_id")
    private Long bookId;

}
