package com.tg.library.dto;

import lombok.Builder;

@Builder
public record BookDTO(Integer id, String title, String alternateTitle, Integer authorId, Integer publisherId,
                      Integer genreId, Integer sectionId, String mainCharacter, Integer seriesId, Integer topicId) {
}
   
