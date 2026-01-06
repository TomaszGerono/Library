package com.tg.library.service;

import com.tg.library.entity.Series;
import com.tg.library.repository.SeriesRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class SeriesService {

    @Autowired
    private SeriesRepository seriesRepository;

    public Long save(SeriesVO vO) {
        Series bean = new Series();
        BeanUtils.copyProperties(vO, bean);
        bean = seriesRepository.save(bean);
        return bean.getSeriesId();
    }

    public void delete(Long id) {
        seriesRepository.deleteById(id);
    }

    public void update(Long id, SeriesUpdateVO vO) {
        Series bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        seriesRepository.save(bean);
    }

    public SeriesDTO getById(Long id) {
        Series original = requireOne(id);
        return toDTO(original);
    }

    public Page<SeriesDTO> query(SeriesQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private SeriesDTO toDTO(Series original) {
        SeriesDTO bean = new SeriesDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private Series requireOne(Long id) {
        return seriesRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
