package com.tg.library.repository;

import com.tg.library.entity.BooksTopics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BooksTopicsRepository extends JpaRepository<BooksTopics, Long>, JpaSpecificationExecutor<BooksTopics> {

}