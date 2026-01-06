package com.tg.library.service;

import com.tg.library.entity.Books;
import com.tg.library.repository.BooksRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class BooksService {

    @Autowired
    private BooksRepository booksRepository;

    public Long save(BooksVO vO) {
        Books bean = new Books();
        BeanUtils.copyProperties(vO, bean);
        bean = booksRepository.save(bean);
        return bean.getBookId();
    }

    public void delete(Long id) {
        booksRepository.deleteById(id);
    }

    public void update(Long id, BooksUpdateVO vO) {
        Books bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        booksRepository.save(bean);
    }

    public BooksDTO getById(Long id) {
        Books original = requireOne(id);
        return toDTO(original);
    }

    public Page<BooksDTO> query(BooksQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private BooksDTO toDTO(Books original) {
        BooksDTO bean = new BooksDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private Books requireOne(Long id) {
        return booksRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
