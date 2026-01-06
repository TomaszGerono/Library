package com.tg.library.repository;

import com.tg.library.entity.Sections;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SectionsRepository extends JpaRepository<Sections, Long>, JpaSpecificationExecutor<Sections> {

}