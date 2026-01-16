package com.tg.library.gui.controller;

import com.tg.library.entity.Progress;
import com.tg.library.entity.Books;
import com.tg.library.gui.util.AuthorsFormatter;
import com.tg.library.gui.view.BooksViewModel;
import com.tg.library.gui.view.SelectionBus;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import org.springframework.stereotype.Component;

@Component
public class BookDetailsController {

    @FXML private Label titleValue;
    @FXML private Label authorValue;
    @FXML private Label genreValue;
    @FXML private Label yearValue;

    @FXML private ComboBox<com.tg.library.entity.Progress> statusCombo;
    @FXML private TextArea notesArea;
    @FXML private Button saveNotesBtn;

    private BooksViewModel vm;
    private boolean internalUpdate = false;

    @FXML
    public void initialize() {
        statusCombo.getItems().setAll(Progress.values());
        setEmptyState();
        SelectionBus.INSTANCE.selectedBookProperty()
                .addListener((obs, old, book) -> showBook(book));

        showBook(null);
    }

    public void bind(BooksViewModel vm) {
        this.vm = vm;

        vm.selectedBookProperty().addListener((obs, o, n) -> {
            internalUpdate = true;
            try { showBook(n); }
            finally { internalUpdate = false; }
        });

        statusCombo.valueProperty().addListener((obs, o, n) -> {
            if (internalUpdate) return;
            Books b = vm.getSelectedBook();
            if (b == null) return;
            // TODO uncomment
//            try {
//                vm.updateStatus(b.getId(), n);
//            } catch (Exception e) {
//                Dialogs.error("Nie udało się zmienić statusu", e.getMessage());
//            }
        });
    }

    @FXML
    public void onSaveNotes() {
        if (vm == null) return;
        Books b = vm.getSelectedBook();
        if (b == null) return;
        // TODO uncomment
//        try {
//            vm.updateNotes(b.getId(), notesArea.getText());
//            Dialogs.info("Zapisano", "Notatki zostały zapisane.");
//        } catch (Exception e) {
//            Dialogs.error("Nie udało się zapisać notatek", e.getMessage());
//        }
    }

    private void showBook(Books book) {

        if (book == null) { setEmptyState(); return; }

        titleValue.setText(safe(book.getTitle()));
        authorValue.setText(safe(AuthorsFormatter.formatAuthors(book.getAuthors())));
  //      genreValue.setText(safe(b.getGenre()));
//        yearValue.setText(b.getPublicationYear() == null ? "" : b.getPublicationYear().toString());
//
        statusCombo.setDisable(false);
//        statusCombo.setValue(b.getStatus());
//
        notesArea.setDisable(false);
       // notesArea.setText(safe(b.getNotes()));
        saveNotesBtn.setDisable(false);
    }

    private void setEmptyState() {
        titleValue.setText("-");
        authorValue.setText("-");
//        genreValue.setText("-");
//        yearValue.setText("-");

        statusCombo.setDisable(true);
        statusCombo.setValue(null);

        notesArea.setDisable(true);
        notesArea.setText("");
        saveNotesBtn.setDisable(true);
    }

    private String safe(String s) { return s == null ? "" : s; }
}

