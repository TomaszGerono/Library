package com.tg.library.service;

import com.tg.library.entity.Authors;
import com.tg.library.repository.AuthorsRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AuthorsService {

    @Autowired
    private AuthorsRepository authorsRepository;

    public Long save(AuthorsVO vO) {
        Authors bean = new Authors();
        BeanUtils.copyProperties(vO, bean);
        bean = authorsRepository.save(bean);
        return bean.getAuthorId();
    }

    public void delete(Long id) {
        authorsRepository.deleteById(id);
    }

    public void update(Long id, AuthorsUpdateVO vO) {
        Authors bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        authorsRepository.save(bean);
    }

    public AuthorsDTO getById(Long id) {
        Authors original = requireOne(id);
        return toDTO(original);
    }

    public Page<AuthorsDTO> query(AuthorsQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private AuthorsDTO toDTO(Authors original) {
        AuthorsDTO bean = new AuthorsDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private Authors requireOne(Long id) {
        return authorsRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
