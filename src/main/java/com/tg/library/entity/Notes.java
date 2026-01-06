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
@Table(name = "notes")
public class Notes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "note_id", nullable = false)
    private Long noteId;

    @Id
    @Column(name = "note", nullable = false)
    private String note;

    @Column(name = "book_id", nullable = false)
    private Long bookId;

}
