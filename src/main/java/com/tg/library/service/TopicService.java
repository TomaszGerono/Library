package com.tg.library.service;

import com.tg.library.entity.Books;
import com.tg.library.entity.Topics;
import com.tg.library.repository.TopicsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.List;

@Service
public class TopicService {

    private final TopicsRepository topicsRepository;

    public List<Topics> findAll() {
        return topicsRepository.findAll();
    }

    public long count() {
        return topicsRepository.count();
    }

    @Autowired
    public TopicService(TopicsRepository topicsRepository) {
        this.topicsRepository = topicsRepository;
    }

    @Transactional
    public Topics create(String name) {
        var topics = new Topics();
        topics.setName(name);
        topicsRepository.saveAndFlush(topics);
        return topics;
    }

//    public void rename(Long id, String newName) {
//        Topics topic = topicsRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Topic not found with id: " + id));
//        topic.setName(newName);
//        topicsRepository.save(topic);
//    }

//    public void deleteById(Long id) {
//        // first remove this topic from all books that reference it
//        List<Books> booksWithTopic = topicsRepository.findAll().stream()
//                .filter(book -> book.getTopics() != null &&
//                        book.getTopics().stream().anyMatch(t -> t.getTopicId().equals(id)))
//                .toList();
//
//        for (Books book : booksWithTopic) {
//            book.getTopics().removeIf(t -> t.getTopicId().equals(id));
//            booksRepository.save(book);
//        }
//
//        // then delete the topic itself
//        topicsRepository.deleteById(id);
//    }

    public void addBookToTopic(Books books, Topics topics) {
            books.getTopics().add(topics);
//            BooksRepository.saveAndFlush(topics);
//
//        Topics topic = topicsRepository.findById(topicId)
//                .orElseThrow(() -> new RuntimeException("Topic not found with id: " + topicId));
//        Books book = booksRepository.findById(bookId)
//                .orElseThrow(() -> new RuntimeException("Book not found with id: " + bookId));
//
//        // check if book already has this topic
//        if (book.getTopics() == null || !book.getTopics().contains(topic)) {
//            book.getTopics().add(topic);
//            booksRepository.save(book);
//        }
    }

    public void removeBookFromTopic(Long topicId, Long bookId) {
//        Books book = booksRepository.findById(bookId)
//                .orElseThrow(() -> new RuntimeException("Book not found with id: " + bookId));
//        Topics topic = topicsRepository.findById(topicId)
//                .orElseThrow(() -> new RuntimeException("Topic not found with id: " + topicId));
//
//        if (book.getTopics() != null) {
//            book.getTopics().remove(topic);
//            booksRepository.save(book);
//        }
    }

    public List<Books> findBooksInTopic(Long topicId) {
//        Topics topic = topicsRepository.findById(topicId)
//                .orElseThrow(() -> new RuntimeException("Topic not found with id: " + topicId));
//
//        // get all books that have this topic in their topics list
//        return booksRepository.findAll().stream()
//                .filter(book -> book.getTopics() != null && book.getTopics().contains(topic))
//                .toList();
//    }
        return null;
    }
}
