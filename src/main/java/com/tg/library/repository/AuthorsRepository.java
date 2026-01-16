package com.tg.library.repository;

import com.tg.library.entity.Authors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

@Component
public interface AuthorsRepository extends JpaRepository<Authors, Long>, JpaSpecificationExecutor<Authors> {
}