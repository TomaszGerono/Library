package com.tg.library.service;

import com.tg.library.entity.BooksTopics;
import com.tg.library.repository.BooksTopicsRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class BooksTopicsService {

    @Autowired
    private BooksTopicsRepository booksTopicsRepository;

    public Long save(BooksTopicsVO vO) {
        BooksTopics bean = new BooksTopics();
        BeanUtils.copyProperties(vO, bean);
        bean = booksTopicsRepository.save(bean);
        return bean.getTopicId();
    }

    public void delete(Long id) {
        booksTopicsRepository.deleteById(id);
    }

    public void update(Long id, BooksTopicsUpdateVO vO) {
        BooksTopics bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        booksTopicsRepository.save(bean);
    }

    public BooksTopicsDTO getById(Long id) {
        BooksTopics original = requireOne(id);
        return toDTO(original);
    }

    public Page<BooksTopicsDTO> query(BooksTopicsQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private BooksTopicsDTO toDTO(BooksTopics original) {
        BooksTopicsDTO bean = new BooksTopicsDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private BooksTopics requireOne(Long id) {
        return booksTopicsRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
