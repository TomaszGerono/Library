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
@Table(name = "books_topics")
@IdClass(BooksTopicsId.class)
public class BooksTopics implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "topic_id")
    private Long topicId;

    @Id
    @Column(name = "book_id")
    private Long bookId;

}
