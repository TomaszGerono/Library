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
@Table(name = "series")
public class Series implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "series_id")
    private Long seriesId;

    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "series_name")
    private String seriesName;

    @Column(name = "author_id")
    private Long authorId;

}
