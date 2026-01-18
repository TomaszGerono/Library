package com.tg.library.service;

import com.tg.library.entity.Books;
import com.tg.library.entity.Topics;
import com.tg.library.repository.BooksRepository;
import com.tg.library.repository.TopicsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {

    BooksRepository bookRepository;
    TopicsRepository topicsRepository;

    @Autowired
    public BookService(BooksRepository bookRepository, TopicsRepository topicsRepository) {
        this.bookRepository = bookRepository;
        this.topicsRepository = topicsRepository;
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

    @Transactional
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public void addBookToTopic(Long bookId, Long topicId) {
        Books b = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found: " + bookId));

        Topics t = topicsRepository.findById(topicId)
                .orElseThrow(() -> new IllegalArgumentException("Topic not found: " + topicId));

        if (b.getTopics() == null) b.setTopics(new java.util.ArrayList<>());

        boolean already = b.getTopics().stream()
                .anyMatch(x -> x != null && x.getId() != null && x.getId().equals(topicId));

        if (!already) {
            b.getTopics().add(t);
            bookRepository.save(b);
        }
    }

}
