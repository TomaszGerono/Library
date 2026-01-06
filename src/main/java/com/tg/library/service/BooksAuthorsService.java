package com.tg.library.service;

import com.tg.library.entity.BooksAuthors;
import com.tg.library.repository.BooksAuthorsRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class BooksAuthorsService {

    @Autowired
    private BooksAuthorsRepository booksAuthorsRepository;

    public Long save(BooksAuthorsVO vO) {
        BooksAuthors bean = new BooksAuthors();
        BeanUtils.copyProperties(vO, bean);
        bean = booksAuthorsRepository.save(bean);
        return bean.getBookId();
    }

    public void delete(Long id) {
        booksAuthorsRepository.deleteById(id);
    }

    public void update(Long id, BooksAuthorsUpdateVO vO) {
        BooksAuthors bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        booksAuthorsRepository.save(bean);
    }

    public BooksAuthorsDTO getById(Long id) {
        BooksAuthors original = requireOne(id);
        return toDTO(original);
    }

    public Page<BooksAuthorsDTO> query(BooksAuthorsQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private BooksAuthorsDTO toDTO(BooksAuthors original) {
        BooksAuthorsDTO bean = new BooksAuthorsDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private BooksAuthors requireOne(Long id) {
        return booksAuthorsRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
