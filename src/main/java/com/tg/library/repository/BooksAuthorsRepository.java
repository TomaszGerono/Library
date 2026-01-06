package com.tg.library.repository;

import com.tg.library.entity.BooksAuthors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BooksAuthorsRepository extends JpaRepository<BooksAuthors, Long>, JpaSpecificationExecutor<BooksAuthors> {

}