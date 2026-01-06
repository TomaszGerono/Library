package com.tg.library.repository;

import com.tg.library.entity.Notes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NotesRepository extends JpaRepository<Notes, String>, JpaSpecificationExecutor<Notes> {

}