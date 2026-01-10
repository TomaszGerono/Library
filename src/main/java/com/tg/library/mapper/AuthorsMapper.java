package com.tg.library.mapper;

import com.tg.library.entity.Authors;
import com.tg.library.dto.AuthorDTO;

public class AuthorsMapper {

    // trzeba zmienic na Author bo liczba mnoga jest myląca
    public static Authors toAuthor(AuthorDTO authorDTO) {

        Authors author = Authors.builder()
                .authorId(authorDTO.id())
                .title(authorDTO.title())
                .firstName(authorDTO.firstName())
                .lastName(authorDTO.lastName())
                .monastery(authorDTO.monastery())
                .middleName(authorDTO.middleName())
                .build();

        return author;

    }

    public static AuthorDTO toAuthorDTO(Authors authors) {
        AuthorDTO authorDTO = AuthorDTO.builder()
                .id(authors.getAuthorId())
                .title(authors.getTitle())
                .firstName(authors.getFirstName())
                .lastName(authors.getLastName())
                .monastery(authors.getMonastery())
                .build();
        return authorDTO;

    }

}
