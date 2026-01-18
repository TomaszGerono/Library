package com.tg.library.repository;

import com.tg.library.entity.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

@Component
public interface SeriesRepository extends JpaRepository<Series, Long>, JpaSpecificationExecutor<Series> {

}