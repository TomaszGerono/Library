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
@Table(name = "topics")
public class Topics implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "topic_id")
    private Long topicId;

    @Column(name = "name", nullable = false)
    private String name;

}
