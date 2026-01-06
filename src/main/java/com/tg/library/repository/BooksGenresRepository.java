package com.tg.library.repository;

import com.tg.library.entity.BooksGenres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BooksGenresRepository extends JpaRepository<BooksGenres, Long>, JpaSpecificationExecutor<BooksGenres> {

}