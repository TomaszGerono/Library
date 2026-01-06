package com.tg.library.entity;

import jakarta.persistence.*;
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
@Table(name = "books")
public class Books implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "book_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "alternate_title")
    private String alternateTitle;

    @Column(name = "genre_id")
    private Long genreId;

    @Column(name = "section_id")
    private Long sectionId;

    @Column(name = "main_character")
    private String mainCharacter;

    @Column(name = "series_id")
    private Long seriesId;

    @Column(name = "topic_id")
    private Long topicId;

}
