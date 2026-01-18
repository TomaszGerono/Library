package com.tg.library.gui.controller;

import com.tg.library.entity.Authors;
import com.tg.library.entity.Books;
import com.tg.library.entity.Genres;
import com.tg.library.gui.util.AuthorsFormatter;
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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Window;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;

@Log4j2
@Component
public class BookFormDialogController {

    public record Result(Books book) {}

    @FXML private TextField titleField;
    @FXML private ListView<Authors> authorsList;
    @FXML private ListView<Authors> selectedAuthorsList;
    @FXML private TextField authorSearchField;
    @FXML private ComboBox<Genres> genreCombo;
    @FXML private TextField yearField;
    @FXML private TextField isbnField;
    @FXML private TextField pagesField;
    @FXML private TextField seriesField;
    @FXML private Label errorLabel;

    @FXML private ButtonType saveButtonType;

    private javafx.collections.ObservableList<Authors> allAuthors =
            javafx.collections.FXCollections.observableArrayList();
    private javafx.collections.ObservableList<Authors> selectedAuthors =
            javafx.collections.FXCollections.observableArrayList();

    private final BookFormViewModel bookFormViewModel = new BookFormViewModel();
    private Books editing;

    @FXML
    public void initialize() {
        titleField.textProperty().bindBidirectional(bookFormViewModel.title);
        //authorField.textProperty().bindBidirectional(bookFormViewModel.author);
        //genreField.textProperty().bindBidirectional(vm.genre);
        // ładne wyświetlanie: "first middle last"
        var cellFactory = (javafx.util.Callback<javafx.scene.control.ListView<Authors>, javafx.scene.control.ListCell<Authors>>) lv ->
                new ListCell<>() {
                    @Override protected void updateItem(Authors a, boolean empty) {
                        super.updateItem(a, empty);
                        setText(empty || a == null ? "" : formatAuthor(a));
                    }
                };

        authorsList.setCellFactory(cellFactory);
        selectedAuthorsList.setCellFactory(cellFactory);

        authorsList.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);

        // proste wyszukiwanie w liście dostępnych autorów
        authorSearchField.textProperty().addListener((obs, o, q) -> applyAuthorFilter(q));
        yearField.textProperty().bindBidirectional(bookFormViewModel.year);
        isbnField.textProperty().bindBidirectional(bookFormViewModel.isbn);
        pagesField.textProperty().bindBidirectional(bookFormViewModel.pages);
        seriesField.textProperty().bindBidirectional(bookFormViewModel.series);
    }

    private void applyAuthorFilter(String q) {
        if (q == null || q.isBlank()) {
            authorsList.setItems(allAuthors);
            return;
        }
        String qq = q.trim().toLowerCase();
        var filtered = allAuthors.filtered(a -> formatAuthor(a).toLowerCase().contains(qq));
        authorsList.setItems(filtered);
    }

    private static String formatAuthor(Authors a) {
        String first = n(a.getFirstName());
        String middle = n(a.getMiddleName());
        String last = n(a.getLastName());

        StringBuilder sb = new StringBuilder();
        if (!first.isBlank()) sb.append(first);
        if (!middle.isBlank()) sb.append(sb.length() > 0 ? " " : "").append(middle);
        if (!last.isBlank()) sb.append(sb.length() > 0 ? " " : "").append(last);

        return sb.toString().trim();
    }

    private static String n(String s) { return s == null ? "" : s.trim(); }

    public void setGenres(java.util.List<Genres> genres) {
        genreCombo.setItems(javafx.collections.FXCollections.observableArrayList(genres));
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
        target.setAuthors(new java.util.ArrayList<>(selectedAuthors));
        bookFormViewModel.applyTo(target);
        return new Result(target);
    }

    public void setData(List<Authors> authors, Books editingBook) {
        allAuthors.setAll(authors == null ? List.of() : authors);
        authorsList.setItems(allAuthors);

        selectedAuthors.clear();
        if (editingBook != null && editingBook.getAuthors() != null) {
            selectedAuthors.addAll(editingBook.getAuthors());
        }
        selectedAuthorsList.setItems(selectedAuthors);
    }

    public static Result showDialog(Window owner, Books editing, java.util.List<Genres> genres, List<Authors> authors) {
        try {
            URL url = BookFormDialogController.class.getResource("/com/tg/library/gui/books/book-form-dialog.fxml");
            if (url == null) {
                throw new IllegalStateException("Nie znaleziono FXML: /com/tg/library/gui/books/book-form-dialog.fxml");
            }

            FXMLLoader loader = new FXMLLoader(url);
            DialogPane pane = loader.load();
            BookFormDialogController controller = loader.getController();
            controller.setGenres(genres);
            controller.setData(authors, editing);
            controller.setEditing(editing);

            Dialog<Result> dialog = new Dialog<>();
            dialog.initOwner(owner);
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle(editing == null ? "Add book" : "Edit book");
            dialog.setDialogPane(pane);

            ButtonType saveType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
            pane.getButtonTypes().setAll(ButtonType.CANCEL, saveType);

            // Walidacja przed zamknięciem
            Button saveBtn = (Button) pane.lookupButton(saveType);
            saveBtn.addEventFilter(ActionEvent.ACTION, ev -> {
                Result r = controller.buildResult();
                if (r == null) {
                    ev.consume(); // ❌ nie zamykaj dialogu
                }
            });

            dialog.setResultConverter(clicked -> {
                if (clicked == saveType) {
                    return controller.buildResult();
                }
                return null;
            });

            return dialog.showAndWait().orElse(null);

        } catch (Exception e) {
            log.error("Error", e);
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onAddAuthorToSelection() {
        var picked = authorsList.getSelectionModel().getSelectedItems();
        if (picked == null || picked.isEmpty()) return;

        for (Authors a : picked) {
            if (!selectedAuthors.contains(a)) selectedAuthors.add(a);
        }
        authorsList.getSelectionModel().clearSelection();
    }

    @FXML
    public void onRemoveAuthorFromSelection() {
        Authors a = selectedAuthorsList.getSelectionModel().getSelectedItem();
        if (a == null) return;
        selectedAuthors.remove(a);
    }

    @FXML
    private void onCreateAuthor() {
        // na razie pusto
    }

}

