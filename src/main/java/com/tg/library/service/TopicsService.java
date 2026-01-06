package com.tg.library.service;

import com.tg.library.entity.Topics;
import com.tg.library.repository.TopicsRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class TopicsService {

    @Autowired
    private TopicsRepository topicsRepository;

    public Long save(TopicsVO vO) {
        Topics bean = new Topics();
        BeanUtils.copyProperties(vO, bean);
        bean = topicsRepository.save(bean);
        return bean.getTopicId();
    }

    public void delete(Long id) {
        topicsRepository.deleteById(id);
    }

    public void update(Long id, TopicsUpdateVO vO) {
        Topics bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        topicsRepository.save(bean);
    }

    public TopicsDTO getById(Long id) {
        Topics original = requireOne(id);
        return toDTO(original);
    }

    public Page<TopicsDTO> query(TopicsQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private TopicsDTO toDTO(Topics original) {
        TopicsDTO bean = new TopicsDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private Topics requireOne(Long id) {
        return topicsRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
