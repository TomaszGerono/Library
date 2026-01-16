package com.tg.library.service;

import com.tg.library.entity.Genres;
import com.tg.library.repository.GenresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenresService {

    GenresRepository genresRepository;

    @Autowired
    public GenresService(GenresRepository genresRepository) {
        this.genresRepository = genresRepository;
    }

    public List<Genres> findAll() {
        return genresRepository.findAll();
    }

}
