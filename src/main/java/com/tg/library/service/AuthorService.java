package com.tg.library.service;

import com.tg.library.entity.Authors;
import com.tg.library.repository.AuthorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    AuthorsRepository authorsRepository;

    @Autowired
    public AuthorService(AuthorsRepository authorsRepository) {
        this.authorsRepository = authorsRepository;
    }

    public List<Authors> findAll() {
        return authorsRepository.findAll();
    }

}