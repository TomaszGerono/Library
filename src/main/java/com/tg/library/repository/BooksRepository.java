package com.tg.library.repository;

import com.tg.library.entity.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BooksRepository extends JpaRepository<Books, Long>, JpaSpecificationExecutor<Books> {

}