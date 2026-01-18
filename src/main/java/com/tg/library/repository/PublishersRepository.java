package com.tg.library.repository;

import com.tg.library.entity.Publishers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PublishersRepository extends JpaRepository<Publishers, Long>, JpaSpecificationExecutor<Publishers> {

}