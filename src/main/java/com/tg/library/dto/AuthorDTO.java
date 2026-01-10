package com.tg.library.dto;

import lombok.Builder;

@Builder
public record AuthorDTO(Long id, String firstName, String middleName, String lastName, String monastery, String title) {
}