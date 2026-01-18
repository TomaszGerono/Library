package com.tg.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class JsonImportService {

    private final tools.jackson.databind.ObjectMapper objectMapper;
    BookService bookService;

    @Autowired
    public JsonImportService(ObjectMapper objectMapper, BookService bookService) {
        this.objectMapper = objectMapper;
        this.bookService = bookService;
    }

    /**
     * Importuje JSON w formie tablicy, np.:
     * [
     *   {"title":"...", "author":"..."},
     *   {"title":"...", "author":"..."}
     * ]
     */
    public <T> List<T> importList(Path jsonFile, Class<T> elementType) throws IOException {
        if (jsonFile == null) {
            throw new IllegalArgumentException("jsonFile is null");
        }
        if (!Files.exists(jsonFile)) {
            throw new IOException("File does not exist: " + jsonFile);
        }
        if (!Files.isRegularFile(jsonFile)) {
            throw new IOException("Not a file: " + jsonFile);
        }
        if (Files.size(jsonFile) == 0) {
            throw new IOException("File is empty: " + jsonFile);
        }

        var javaType = objectMapper.getTypeFactory()
                .constructCollectionType(List.class, elementType);

        return objectMapper.readValue(jsonFile.toFile(), javaType);
    }

}
