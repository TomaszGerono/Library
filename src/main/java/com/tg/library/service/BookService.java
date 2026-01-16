package com.tg.library.service;

import com.tg.library.entity.Books;
import com.tg.library.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {

    BooksRepository bookRepository;

    @Autowired
    public BookService(BooksRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional
    public Books add(Books books) {

        return bookRepository.saveAndFlush(books);

    }

    public Books update(Books books) {

        return bookRepository.save(books);

    }

    public void remove(Books books) {

        bookRepository.delete(books);

    }

    public Books find(Books books) {
        return null;
    }

    public List<Books> findAll() {
        return bookRepository.findAll();
    }

}
