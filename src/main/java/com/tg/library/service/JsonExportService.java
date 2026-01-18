package com.tg.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Path;

@Service
public class JsonExportService {

    private final ObjectMapper objectMapper;
    private final BookService bookService;

    @Autowired
    public JsonExportService(ObjectMapper objectMapper, BookService bookService) {
        this.objectMapper = objectMapper;
        this.bookService = bookService;
    }

    public void exportToJson(Object data, Path filePath) throws IOException {
        objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValue(filePath.toFile(), data);
    }
}