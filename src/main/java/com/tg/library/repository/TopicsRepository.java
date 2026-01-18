package com.tg.library.repository;

import com.tg.library.entity.Books;
import com.tg.library.entity.Topics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicsRepository extends JpaRepository<Topics, Long>, JpaSpecificationExecutor<Topics> {

    @Query("""
            select b from Books b
            join b.topics t
            where t.id = :topicId
            """)
    List<Books> findBooksByTopicId(@Param("topicId") Long topicId);

}