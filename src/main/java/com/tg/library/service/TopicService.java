package com.tg.library.service;

import com.tg.library.entity.Topics;
import com.tg.library.repository.TopicsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {
    @Autowired
    private TopicsRepository topicsRepository; // or however you access your data

    public List<Topics> findAll() {
        return topicsRepository.findAll();
    }

    public long count() {
        return topicsRepository.count();
    }
}
