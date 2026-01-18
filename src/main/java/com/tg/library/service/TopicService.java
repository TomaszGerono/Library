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

    public List<Books> findBooksByTopicId(Long topicId) {
        return topicsRepository.findBooksByTopicId(topicId);
    }

    @Transactional
    public Topics add(Topics topic) {
        var newTopic = topicsRepository.saveAndFlush(topic);
        return newTopic;
    }

    public void deleteTopic(Long topicId) {
        topicsRepository.delete(new Topics().builder().id(topicId).build());
    }

    @Transactional
    public void removeBookFromTopic(Long bookId, Long topicId) {
        topicsRepository.unlinkBookFromTopic(bookId, topicId);
    }

}
