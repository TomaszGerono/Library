package com.tg.library.service;

import com.tg.library.entity.Genres;
import com.tg.library.repository.GenresRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class GenresService {

    @Autowired
    private GenresRepository genresRepository;

    public Long save(GenresVO vO) {
        Genres bean = new Genres();
        BeanUtils.copyProperties(vO, bean);
        bean = genresRepository.save(bean);
        return bean.getGenreId();
    }

    public void delete(Long id) {
        genresRepository.deleteById(id);
    }

    public void update(Long id, GenresUpdateVO vO) {
        Genres bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        genresRepository.save(bean);
    }

    public GenresDTO getById(Long id) {
        Genres original = requireOne(id);
        return toDTO(original);
    }

    public Page<GenresDTO> query(GenresQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private GenresDTO toDTO(Genres original) {
        GenresDTO bean = new GenresDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private Genres requireOne(Long id) {
        return genresRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
