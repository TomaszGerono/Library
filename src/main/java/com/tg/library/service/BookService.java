package com.tg.library.service;

import com.tg.library.mapper.BooksMapper;
import com.tg.library.model.BookDTO;
import com.tg.library.repository.BookRepository;

public class BookService {
// Asia tutaj dodajesz sobie metody jakie potrzebujesz

    // TODO sprawdzic jak wire'owac Asia
    BookRepository bookRepository;

    /**
     * Adding book to repository
     * @param bookDTO
     */
    public void add(BookDTO bookDTO) {

        bookRepository.save(BooksMapper.toBook(bookDTO));

    }

    public void update(BookDTO bookDTO) {

        bookRepository.save(BooksMapper.toBook(bookDTO));

    }

    public void remove(BookDTO bookDTO) {

        bookRepository.delete(BooksMapper.toBook(bookDTO));

    }

    public BookDTO find(BookDTO bookDTO) {
        return null;
    }

}
