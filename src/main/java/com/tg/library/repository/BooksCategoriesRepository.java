package com.tg.library.repository;

import com.tg.library.entity.BooksCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BooksCategoriesRepository extends JpaRepository<BooksCategories, Long>, JpaSpecificationExecutor<BooksCategories> {

}