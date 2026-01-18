package com.tg.library.repository;

import com.tg.library.entity.Books;
import com.tg.library.entity.Topics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicsRepository extends JpaRepository<Topics, Long>, JpaSpecificationExecutor<Topics> {

    @Query("""
            select b from Books b
            join b.topics t
            where t.id = :topicId
            """)
    List<Books> findBooksByTopicId(@Param("topicId") Long topicId);

    @Modifying
    @Query(value = "delete from book_topics where book_id = :bookId and topic_id = :topicId", nativeQuery = true)
    void unlinkBookFromTopic(@Param("bookId") Long bookId, @Param("topicId") Long topicId);

    Optional<Topics> findByNameIgnoreCase(String name);

}