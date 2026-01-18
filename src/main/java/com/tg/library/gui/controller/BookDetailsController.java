package com.tg.library.gui.controller;

import com.tg.library.entity.Books;
import com.tg.library.entity.Progress;
import com.tg.library.entity.Publishers;
import com.tg.library.gui.util.AuthorsFormatter;
import com.tg.library.gui.view.BooksViewModel;
import com.tg.library.gui.view.SelectionBus;
import com.tg.library.service.BookService;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.stereotype.Component;

import java.awt.print.Book;
import java.util.Optional;

@Component
public class BookDetailsController {

    private final BookService bookService;

    public BookDetailsController(BookService bookService) {
        this.bookService = bookService;
    }

    @FXML private Label titleValue;
    @FXML private Label authorsValue;
    @FXML private Label genreValue;
    @FXML private Label yearValue;
    @FXML private Label isbnValue;
    @FXML private Label monasteryValue;
    @FXML private Label publisherValue;
    @FXML private Label seriesValue;
    @FXML private Label pagesValue;
    @FXML private ComboBox<Progress> statusCombo;
    @FXML private TextArea notesArea;
    @FXML private Button saveNotesBtn;

    private BooksViewModel vm;

    private boolean internalUpdate = false;
    private boolean dirty = false;

    @FXML
    public void initialize() {
        statusCombo.getItems().setAll(Progress.values());

        // reaguj na wybór w tabeli przez SelectionBus
        SelectionBus.INSTANCE.selectedBookProperty()
                .addListener((obs, old, book) -> showBook(book));

        // zmiana statusu -> oznacz zmiany (i opcjonalnie auto-zapis)
        statusCombo.valueProperty().addListener((obs, o, n) -> {
            if (internalUpdate) return;
            if (getCurrentBook() == null) return;
            markDirty();
        });

        // zmiana notatek -> oznacz zmiany
        notesArea.textProperty().addListener((obs, o, n) -> {
            if (internalUpdate) return;
            if (getCurrentBook() == null) return;
            markDirty();
        });

        setEmptyState();
    }

    public void bind(BooksViewModel vm) {
        this.vm = vm;
        vm.selectedBookProperty().addListener((obs, o, n) -> {
            internalUpdate = true;
            try { showBook(n); }
            finally { internalUpdate = false; }
        });
    }

    @FXML
    public void onSaveNotes() {
        Books b = getCurrentBook();
        if (b == null) return;

        // zbierz dane z UI
        Progress newStatus = statusCombo.getValue();
        String newNotes = notesArea.getText();

        // blokada UI podczas zapisu
        setSavingState(true);

        Task<Books> task = new Task<>() {
            @Override
            protected Books call() {
                // ustaw pola i zapisz
                b.setReadingProgress(newStatus);

                // notes zakładamy jako String w encji
                b.setNotes(newNotes);

                // DOPASUJ metodę:
                // return bookService.update(b);
                Books book = bookService.update(b);
                SelectionBus.INSTANCE.fireBooksChanged();
                return book;
            }
        };

        task.setOnSucceeded(e -> {
            Books saved = task.getValue();

            internalUpdate = true;
            try {
                showBook(saved);

                SelectionBus.INSTANCE.setSelectedBook(saved);
                if (vm != null) vm.setSelectedBook(saved);

                dirty = false;
                saveNotesBtn.setDisable(true);
                SelectionBus.INSTANCE.fireBooksChanged();
            } finally {
                internalUpdate = false;
                setSavingState(false);
            }
        });

        task.setOnFailed(e -> {
            setSavingState(false);
            Throwable ex = task.getException();
            showError("Nie udało się zapisać", ex == null ? "" : ex.getMessage());
        });

        Thread t = new Thread(task, "fx-save-book-details");
        t.setDaemon(true);
        t.start();
    }

    private void showBook(Books book) {
        internalUpdate = true;
        try {
            if (book == null) {
                setEmptyState();
                return;
            }

            titleValue.setText(safe(book.getTitle()));
            authorsValue.setText(safe(AuthorsFormatter.formatAuthors(book.getAuthors())));

            genreValue.setText(book.getGenre() == null ? "-" : safe(book.getGenre().getName()));
            yearValue.setText(book.getPublicationYear() == null ? "-" : book.getPublicationYear().toString());

            isbnValue.setText(book.getIsbn());
            monasteryValue.setText(book.getMonastery());
            publisherValue.setText(
                    Optional.ofNullable(book)
                            .map(Books::getPublisher)
                            .map(Publishers::getName)
                            .orElse("")
            );
            seriesValue.setText(
                    Optional.ofNullable(book.getSerie())
                            .map(s -> s.getSeriesName())
                            .orElse("")
            );

            statusCombo.setDisable(false);
            statusCombo.setValue(book.getReadingProgress());

            pagesValue.setText(Optional.ofNullable(book.getPagesCount())
                    .map(s -> s.toString())
                    .orElse(""));
            notesArea.setDisable(false);
            notesArea.setText(book.getNotes() == null ? "" : book.getNotes());

            saveNotesBtn.setDisable(!dirty); // jeśli nie ma zmian, to disabled
        } finally {
            internalUpdate = false;
        }
    }

    private void setEmptyState() {
        internalUpdate = true;
        try {
            titleValue.setText("-");
            authorsValue.setText("-");
            genreValue.setText("-");
            yearValue.setText("-");
            isbnValue.setText("-");
            monasteryValue.setText("-");
            publisherValue.setText("-");
            seriesValue.setText("-");

            statusCombo.setDisable(true);
            statusCombo.setValue(null);

            notesArea.setDisable(true);
            notesArea.setText("");

            dirty = false;
            saveNotesBtn.setDisable(true);
        } finally {
            internalUpdate = false;
        }
    }

    private void markDirty() {
        dirty = true;
        saveNotesBtn.setDisable(false);
    }

    private void setSavingState(boolean saving) {
        statusCombo.setDisable(saving);
        notesArea.setDisable(saving);
        saveNotesBtn.setDisable(saving || !dirty);
    }

    private Books getCurrentBook() {
        Books b = SelectionBus.INSTANCE.getSelectedBook();
        if (b != null) return b;
        return vm == null ? null : vm.getSelectedBook();
    }

    private void showError(String title, String msg) {
        Platform.runLater(() -> {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle(title);
            a.setHeaderText(title);
            a.setContentText(msg);
            a.showAndWait();
        });
    }

    private String safe(String s) { return s == null ? "" : s; }
}
