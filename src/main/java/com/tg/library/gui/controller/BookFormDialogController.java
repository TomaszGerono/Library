package com.tg.library.gui.controller;

import com.tg.library.entity.Books;
import com.tg.library.entity.Genres;
import com.tg.library.gui.view.BookFormViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.net.URL;

@Log4j2
@Component
public class BookFormDialogController {

    public record Result(Books book) {}

    @FXML private TextField titleField;
    @FXML private TextField authorField;
    @FXML private ComboBox<Genres> genreCombo;
    @FXML private TextField yearField;
    @FXML private TextField isbnField;
    @FXML private TextField pagesField;
    @FXML private TextField seriesField;
    @FXML private Label errorLabel;

    @FXML private ButtonType saveButtonType;

    private final BookFormViewModel bookFormViewModel = new BookFormViewModel();
    private Books editing;

    @FXML
    public void initialize() {
        titleField.textProperty().bindBidirectional(bookFormViewModel.title);
        authorField.textProperty().bindBidirectional(bookFormViewModel.author);
        //genreField.textProperty().bindBidirectional(vm.genre);
        yearField.textProperty().bindBidirectional(bookFormViewModel.year);
        isbnField.textProperty().bindBidirectional(bookFormViewModel.isbn);
        pagesField.textProperty().bindBidirectional(bookFormViewModel.pages);
        seriesField.textProperty().bindBidirectional(bookFormViewModel.series);
    }

    public void setGenres(java.util.List<Genres> genres) {
        genreCombo.setItems(javafx.collections.FXCollections.observableArrayList(genres));
        // opcjonalnie: pozwól na brak gatunku
        genreCombo.getItems().add(0, null);

        genreCombo.setConverter(new javafx.util.StringConverter<>() {
            @Override public String toString(Genres g) {
                return g == null ? "" : g.getName();
            }
            @Override public Genres fromString(String s) { return null; } // nie używamy
        });
    }

    private void setEditing(Books b) {

        this.editing = b;

        errorLabel.setVisible(false);
        errorLabel.setText("");

        if (b == null) {
            bookFormViewModel.clear(); // dodaj w VM (poniżej) albo ręcznie wyczyść pola
            genreCombo.getSelectionModel().clearSelection();
            return;
        }

        bookFormViewModel.fromBook(b);
        genreCombo.getSelectionModel().select(b.getGenre());

    }

    private Result buildResult() {
        String err = bookFormViewModel.validate();
        if (err != null) {
            errorLabel.setText(err);
            errorLabel.setVisible(true);
            return null;
        }
        Books target = (editing != null) ? editing : new Books();
        target.setGenre(genreCombo.getValue()); // może być null – to OK
        bookFormViewModel.applyTo(target);
        return new Result(target);
    }

    public static Result showDialog(Window owner, Books editing, java.util.List<Genres> genres) {
        try {
            URL url = BookFormDialogController.class.getResource("/com/tg/library/gui/books/book-form-dialog.fxml");
            if (url == null) {
                throw new IllegalStateException("Nie znaleziono FXML: /com/tg/library/gui/books/book-form-dialog.fxml");
            }

            FXMLLoader loader = new FXMLLoader(url);
            DialogPane pane = loader.load();
            BookFormDialogController c = loader.getController();
            c.setGenres(genres);
            c.setEditing(editing);

            Dialog<Result> dialog = new Dialog<>();
            dialog.initOwner(owner);
            dialog.setTitle(editing == null ? "Add book" : "Edit book");
            dialog.setDialogPane(pane);

            ButtonType saveType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
            pane.getButtonTypes().setAll(ButtonType.CANCEL, saveType);

            // Walidacja przed zamknięciem
            Button saveBtn = (Button) pane.lookupButton(saveType);
            saveBtn.addEventFilter(ActionEvent.ACTION, ev -> {
                Result r = c.buildResult();
                if (r == null) {
                    ev.consume(); // ❌ nie zamykaj dialogu
                }
            });

            // Konwersja: przy OK zwróć Result, inaczej null
            dialog.setResultConverter(clicked -> {
                if (clicked == saveType) {
                    return c.buildResult();
                }
                return null;
            });

            return dialog.showAndWait().orElse(null);

        } catch (Exception e) {
            // obsłuż komunikat
            log.error("Error", e);
            throw new RuntimeException(e);
        }
    }

}

