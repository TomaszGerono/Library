package com.tg.library.service;

import com.tg.library.entity.Sections;
import com.tg.library.repository.SectionsRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class SectionsService {

    @Autowired
    private SectionsRepository sectionsRepository;

    public Long save(SectionsVO vO) {
        Sections bean = new Sections();
        BeanUtils.copyProperties(vO, bean);
        bean = sectionsRepository.save(bean);
        return bean.getSectionId();
    }

    public void delete(Long id) {
        sectionsRepository.deleteById(id);
    }

    public void update(Long id, SectionsUpdateVO vO) {
        Sections bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        sectionsRepository.save(bean);
    }

    public SectionsDTO getById(Long id) {
        Sections original = requireOne(id);
        return toDTO(original);
    }

    public Page<SectionsDTO> query(SectionsQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private SectionsDTO toDTO(Sections original) {
        SectionsDTO bean = new SectionsDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private Sections requireOne(Long id) {
        return sectionsRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
