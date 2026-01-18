package com.tg.library.gui.controller;

import com.tg.library.entity.Books;
import com.tg.library.entity.Genres;
import com.tg.library.entity.Progress;
import com.tg.library.gui.view.SelectionBus;
import com.tg.library.service.BookService;
import com.tg.library.service.CategoriesService;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OverviewController {

    private final BookService bookService;
    private final CategoriesService categoriesService;

    public OverviewController(BookService bookService, CategoriesService categoriesService) {
        this.bookService = bookService;
        this.categoriesService = categoriesService;
    }

    @FXML private Label totalBooksLabel;
    @FXML private Label totalCategoriesLabel;
    @FXML private Label unreadLabel;
    @FXML private Label readingLabel;
    @FXML private Label completedLabel;

    @FXML private ListView<Books> recommendationsList;

    @FXML
    public void initialize() {

        recommendationsList.setCellFactory(lv -> new ListCell<>() {
            @Override protected void updateItem(Books b, boolean empty) {
                super.updateItem(b, empty);
                if (empty || b == null) { setText(null); return; }

                String genre = (b.getGenre() == null) ? "" : safe(b.getGenre().getName());
                String year = (b.getPublicationYear() == null) ? "" : " (" + b.getPublicationYear() + ")";
                setText(safe(b.getTitle()) + year + (genre.isBlank() ? "" : " • " + genre));
            }
        });

        SelectionBus.INSTANCE.booksChangedProperty().addListener((obs, o, n) -> loadAsync());

        loadAsync();
    }

    private void loadAsync() {
        Task<OverviewData> task = new Task<>() {
            @Override
            protected OverviewData call() {
                List<Books> books = Optional.ofNullable(bookService.findAll()).orElse(List.of());

                long unread = books.stream().filter(b -> b != null && b.getReadingProgress() == Progress.unread).count();
                long reading = books.stream().filter(b -> b != null && b.getReadingProgress() == Progress.reading).count();
                long completed = books.stream().filter(b -> b != null && b.getReadingProgress() == Progress.completed).count();

                int categoriesCount;
                try {
                    categoriesCount = categoriesService.findAll().size();
                } catch (Exception e) {
                    categoriesCount = (int) books.stream()
                            .map(Books::getCategory)
                            .filter(Objects::nonNull)
                            .map(c -> c.getId() == null ? c.getName() : c.getId().toString())
                            .distinct()
                            .count();
                }

                List<Books> rec = recommend(books, 8);
                return new OverviewData(books.size(), categoriesCount, unread, reading, completed, rec);
            }
        };

        task.setOnSucceeded(e -> apply(task.getValue()));
        task.setOnFailed(e -> setDash());

        Thread t = new Thread(task, "fx-overview-load");
        t.setDaemon(true);
        t.start();
    }

    private void apply(OverviewData d) {
        if (d == null) { setDash(); return; }

        totalBooksLabel.setText(String.valueOf(d.totalBooks));
        totalCategoriesLabel.setText(String.valueOf(d.totalCategories));
        unreadLabel.setText(String.valueOf(d.unread));
        readingLabel.setText(String.valueOf(d.reading));
        completedLabel.setText(String.valueOf(d.completed));

        recommendationsList.getItems().setAll(d.recommendations);
        if (!recommendationsList.getItems().isEmpty()) {
            recommendationsList.getSelectionModel().selectFirst(); // zaznacz pierwszy
        }
    }

    private void setDash() {
        totalBooksLabel.setText("-");
        totalCategoriesLabel.setText("-");
        unreadLabel.setText("-");
        readingLabel.setText("-");
        completedLabel.setText("-");
        recommendationsList.getItems().clear();
    }

    /**
     * Simple heuristics:
     * 1) take all unread books
     * 2) prefer the most common genre in the whole library
     * 3) if no unread - pick random from the whole library
     */
    private List<Books> recommend(List<Books> books, int limit) {
        if (books == null || books.isEmpty()) return List.of();

        // top genre w bibliotece
        Map<Long, Long> genreFreq = books.stream()
                .map(Books::getGenre)
                .filter(Objects::nonNull)
                .filter(g -> g.getId() != null)
                .collect(Collectors.groupingBy(Genres::getId, Collectors.counting()));

        Long topGenreId = genreFreq.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        List<Books> unread = books.stream()
                .filter(Objects::nonNull)
                .filter(b -> b.getReadingProgress() == Progress.unread)
                .toList();

        List<Books> preferred = (topGenreId == null) ? List.of()
                : unread.stream()
                .filter(b -> b.getGenre() != null && topGenreId.equals(b.getGenre().getId()))
                .toList();

        List<Books> result = new ArrayList<>();
        result.addAll(preferred.stream().limit(limit).toList());

        if (result.size() < limit) {
            Set<Long> already = result.stream()
                    .map(Books::getId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            unread.stream()
                    .filter(b -> b.getId() == null || !already.contains(b.getId()))
                    .limit(limit - result.size())
                    .forEach(result::add);
        }

        if (result.isEmpty()) {
            List<Books> copy = new ArrayList<>(books);
            Collections.shuffle(copy);
            return copy.stream().limit(limit).toList();
        }

        return result;
    }

    private static String safe(String s) { return s == null ? "" : s; }

    private record OverviewData(
            int totalBooks,
            int totalCategories,
            long unread,
            long reading,
            long completed,
            List<Books> recommendations
    ) {}
}
