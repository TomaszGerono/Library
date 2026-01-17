package com.tg.library;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class AppContext {
//    private final BookService bookService;
//    private final ShelfService shelfService;
//    private final ImportExportService importExportService;
//    private final RecommendationService recommendationService;
//
//    public AppContext() {
//        // Podmień na Twoje implementacje / wstrzykiwanie
//        this.bookService = new InMemoryBookService();
//        this.shelfService = new InMemoryShelfService();
//        this.importExportService = new StubImportExportService();
//        this.recommendationService = new StubRecommendationService();
//    }
//
//    public BookService books() { return bookService; }
//    public ShelfService topics() { return shelfService; }
//    public ImportExportService importExport() { return importExportService; }
//    public RecommendationService recommendations() { return recommendationService; }

    private final ApplicationContext springContext;

    public AppContext(ApplicationContext springContext) {
        this.springContext = springContext;
    }

    public ApplicationContext getSpringContext() {
        return springContext;
    }

}

