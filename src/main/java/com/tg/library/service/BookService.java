package com.tg.library.service;

import com.tg.library.entity.Books;
import com.tg.library.mapper.BooksMapper;
import com.tg.library.dto.BookDTO;
import com.tg.library.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    BooksRepository bookRepository;

    @Autowired
    public BookService(BooksRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

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

    public List<Books> findAll() {
        return bookRepository.findAll();
    }

}
