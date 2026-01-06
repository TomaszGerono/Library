package com.tg.library.service;

import com.tg.library.entity.Publishers;
import com.tg.library.repository.PublishersRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class PublishersService {

    @Autowired
    private PublishersRepository publishersRepository;

    public Long save(PublishersVO vO) {
        Publishers bean = new Publishers();
        BeanUtils.copyProperties(vO, bean);
        bean = publishersRepository.save(bean);
        return bean.getPublisherId();
    }

    public void delete(Long id) {
        publishersRepository.deleteById(id);
    }

    public void update(Long id, PublishersUpdateVO vO) {
        Publishers bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        publishersRepository.save(bean);
    }

    public PublishersDTO getById(Long id) {
        Publishers original = requireOne(id);
        return toDTO(original);
    }

    public Page<PublishersDTO> query(PublishersQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private PublishersDTO toDTO(Publishers original) {
        PublishersDTO bean = new PublishersDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private Publishers requireOne(Long id) {
        return publishersRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
