package com.tg.library.mapper;

import com.tg.library.entity.Books;
import com.tg.library.model.BookDTO;

public class BooksMapper {

    public static Books toBook(BookDTO bookDTO) {
        Books book = new Books();
        return book; // TODO przepisac dane builderem podobnie jak w AuthorsMapper
    }

    public static BookDTO toBookDTO(Books book) {
        BookDTO bookDTO = new BookDTO.Builder()
                .id(book.getBookId()) // TODO przepisac reszte pol
                .build();
    }


}
