package com.tg.library.repository;

import com.tg.library.entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

@Component
public interface CategoriesRepository extends JpaRepository<Categories, Long>, JpaSpecificationExecutor<Categories> {

}