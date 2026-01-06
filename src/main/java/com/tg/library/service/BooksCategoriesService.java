package com.tg.library.service;

import com.tg.library.entity.BooksCategories;
import com.tg.library.repository.BooksCategoriesRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class BooksCategoriesService {

    @Autowired
    private BooksCategoriesRepository booksCategoriesRepository;

    public Long save(BooksCategoriesVO vO) {
        BooksCategories bean = new BooksCategories();
        BeanUtils.copyProperties(vO, bean);
        bean = booksCategoriesRepository.save(bean);
        return bean.getCategoryId();
    }

    public void delete(Long id) {
        booksCategoriesRepository.deleteById(id);
    }

    public void update(Long id, BooksCategoriesUpdateVO vO) {
        BooksCategories bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        booksCategoriesRepository.save(bean);
    }

    public BooksCategoriesDTO getById(Long id) {
        BooksCategories original = requireOne(id);
        return toDTO(original);
    }

    public Page<BooksCategoriesDTO> query(BooksCategoriesQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private BooksCategoriesDTO toDTO(BooksCategories original) {
        BooksCategoriesDTO bean = new BooksCategoriesDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private BooksCategories requireOne(Long id) {
        return booksCategoriesRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
