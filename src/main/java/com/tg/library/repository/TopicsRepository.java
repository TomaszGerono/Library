package com.tg.library.repository;

import com.tg.library.entity.Topics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TopicsRepository extends JpaRepository<Topics, Long>, JpaSpecificationExecutor<Topics> {

}