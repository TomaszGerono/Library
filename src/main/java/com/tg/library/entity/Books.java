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
@Table(name = "books")
public class Books implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "book_id", nullable = false)
    private Integer bookId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "alternate_title")
    private String alternateTitle;

    @Column(name = "genre_id")
    private Integer genreId;

    @Column(name = "section_id")
    private Integer sectionId;

    @Column(name = "main_character")
    private String mainCharacter;

    @Column(name = "series_id")
    private Integer seriesId;

    @Column(name = "topic_id")
    private Integer topicId;

}
