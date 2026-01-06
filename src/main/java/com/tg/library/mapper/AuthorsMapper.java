package com.tg.library.mapper;

import com.tg.library.entity.Authors;
import com.tg.library.model.AuthorDTO;

public class AuthorsMapper {

    // trzeba zmienic na Author bo liczba mnoga jest myląca
    public static Authors toAuthor(AuthorDTO authorDTO) {

        Authors author = new Authors().builder()
                .authorId(authorDTO.id())
                .title(authorDTO.title())
                .firstName(authorDTO.firstName())
                .lastName(authorDTO.lastName())
                .monastery(authorDTO.monastery())
                .build();

        return author;

    }

    public static AuthorDTO toAuthorDTO(Authors authors) {
        AuthorDTO authorDTO = new AuthorDTO.Builder()
                .id(authors.getAuthorId()) // withId
                .title(authors.getTitle()) // withTitle
                .firstName(authors.getFirstName())
                .lastName(authors.getLastName())
                .monastery(authors.getMonastery())
                .build();

        return authorDTO;

    }

}
