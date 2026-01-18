package com.tg.library.service.dto;

public record BookImportDto(
        String title,
        String isbn,
        Integer publicationYear,
        Integer pagesCount,
        String readingProgress, // "unread"/"reading"/"completed"
        String notes,
        String genre,           // np. "fiction"
        java.util.List<AuthorImportDto> authors,
        java.util.List<String> topics,
        Integer seriesId,
        String monastery
) {}