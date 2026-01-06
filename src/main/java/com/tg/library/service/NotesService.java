package com.tg.library.service;

import com.tg.library.entity.Notes;
import com.tg.library.repository.NotesRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class NotesService {

    @Autowired
    private NotesRepository notesRepository;

    public String save(NotesVO vO) {
        Notes bean = new Notes();
        BeanUtils.copyProperties(vO, bean);
        bean = notesRepository.save(bean);
        return bean.getNote();
    }

    public void delete(String id) {
        notesRepository.deleteById(id);
    }

    public void update(String id, NotesUpdateVO vO) {
        Notes bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        notesRepository.save(bean);
    }

    public NotesDTO getById(String id) {
        Notes original = requireOne(id);
        return toDTO(original);
    }

    public Page<NotesDTO> query(NotesQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private NotesDTO toDTO(Notes original) {
        NotesDTO bean = new NotesDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private Notes requireOne(String id) {
        return notesRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
