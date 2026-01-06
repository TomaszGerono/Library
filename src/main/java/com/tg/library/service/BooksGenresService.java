package com.tg.library.service;

import com.tg.library.entity.BooksGenres;
import com.tg.library.repository.BooksGenresRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class BooksGenresService {

    @Autowired
    private BooksGenresRepository booksGenresRepository;

    public Long save(BooksGenresVO vO) {
        BooksGenres bean = new BooksGenres();
        BeanUtils.copyProperties(vO, bean);
        bean = booksGenresRepository.save(bean);
        return bean.getGenreId();
    }

    public void delete(Long id) {
        booksGenresRepository.deleteById(id);
    }

    public void update(Long id, BooksGenresUpdateVO vO) {
        BooksGenres bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        booksGenresRepository.save(bean);
    }

    public BooksGenresDTO getById(Long id) {
        BooksGenres original = requireOne(id);
        return toDTO(original);
    }

    public Page<BooksGenresDTO> query(BooksGenresQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private BooksGenresDTO toDTO(BooksGenres original) {
        BooksGenresDTO bean = new BooksGenresDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private BooksGenres requireOne(Long id) {
        return booksGenresRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
