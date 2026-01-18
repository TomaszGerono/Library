package com.tg.library.repository;

import com.tg.library.entity.Genres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

@Component
public interface GenresRepository extends JpaRepository<Genres, Long>, JpaSpecificationExecutor<Genres> {

}