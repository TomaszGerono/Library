package com.tg.library.mapper;

import com.tg.library.entity.Books;
import com.tg.library.dto.BookDTO;

public class BooksMapper {

    public static Books toBook(BookDTO bookDTO) {
        Books book = Books.builder()
                .bookId(bookDTO.id())
                .genreId(bookDTO.genreId())
                .title(bookDTO.title())
                .alternateTitle(bookDTO.alternateTitle())
                .mainCharacter(bookDTO.mainCharacter())
                .sectionId(bookDTO.sectionId())
                .seriesId(bookDTO.seriesId())
                .topicId(bookDTO.topicId())
                .build();
        return book;
    }

    public static BookDTO toBookDTO(Books book) {

        BookDTO bookDTO = BookDTO.builder()
                .genreId(book.getGenreId())
                .title(book.getTitle())
                .alternateTitle(book.getAlternateTitle())
                .mainCharacter(book.getMainCharacter())
                .sectionId(book.getSectionId())
                .seriesId(book.getSeriesId())
                .topicId(book.getTopicId())
                .build();

        return bookDTO;

    }


}
