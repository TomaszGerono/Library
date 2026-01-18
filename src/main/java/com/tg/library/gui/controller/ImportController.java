package com.tg.library.gui.controller;

import com.tg.library.gui.view.SelectionBus;
import com.tg.library.service.GoogleBooksImportService;
import com.tg.library.service.JsonImportService;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

@Log4j2
@Component
public class ImportController {

    public enum Source { JSON_FILE, GOOGLE_BOOKS }

    private final GoogleBooksImportService googleBooksImportService;
    private final JsonImportService jsonImportService;

    @FXML private ChoiceBox<Source> sourceChoiceBox;

    // JSON
    @FXML private VBox jsonPane;
    @FXML private TextField pathDisplayField;
    private File selectedFile;

    // Google
    @FXML private VBox googlePane;
    @FXML private TextField googleQueryField;
    @FXML private Spinner<Integer> maxResultsSpinner;
    @FXML private CheckBox onlyBooksCheckBox;

    // Common
    @FXML private Button confirmButton;
    @FXML private Label statusLabel;

    @Autowired
    public ImportController(JsonImportService jsonImportService,
                            GoogleBooksImportService googleBooksImportService) {
        this.jsonImportService = jsonImportService;
        this.googleBooksImportService = googleBooksImportService;
    }

    @FXML
    public void initialize() {
        sourceChoiceBox.getItems().setAll(Source.JSON_FILE, Source.GOOGLE_BOOKS);
        sourceChoiceBox.setValue(Source.JSON_FILE);

        maxResultsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 40, 10));

        // przełączanie paneli
        sourceChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            boolean json = newV == Source.JSON_FILE;
            setPaneVisible(jsonPane, json);
            setPaneVisible(googlePane, !json);

            statusLabel.setText("");
            confirmButton.setDisable(true);

            // reset
            selectedFile = null;
            pathDisplayField.clear();
            googleQueryField.clear();
        });

        // walidacja
        googleQueryField.textProperty().addListener((obs, o, n) -> updateConfirmEnabled());

        // stan początkowy
        setPaneVisible(jsonPane, true);
        setPaneVisible(googlePane, false);
        updateConfirmEnabled();
    }

    private void setPaneVisible(Region pane, boolean visible) {
        if (pane == null) return;
        pane.setVisible(visible);
        pane.setManaged(visible);
    }

    private void updateConfirmEnabled() {
        Source src = sourceChoiceBox.getValue();
        boolean enabled = switch (src) {
            case JSON_FILE -> selectedFile != null;
            case GOOGLE_BOOKS -> googleQueryField.getText() != null && !googleQueryField.getText().trim().isEmpty();
        };
        if (confirmButton != null) confirmButton.setDisable(!enabled);
    }

    @FXML
    private void onSelectFile() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select JSON file");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files", "*.json"));

        File f = fc.showOpenDialog(pathDisplayField.getScene().getWindow());
        if (f != null) {
            selectedFile = f;
            pathDisplayField.setText(f.getAbsolutePath());
            statusLabel.setText("");
            updateConfirmEnabled();
        }
    }

    @FXML
    private void onConfirmImport() {
        statusLabel.setText("");

        Source src = sourceChoiceBox.getValue();

        if (src == Source.JSON_FILE) {
            if (selectedFile == null) {
                statusLabel.setText("Please select a JSON file.");
                return;
            }

            confirmButton.setDisable(true);
            statusLabel.setText("Importing...");

            runAsync("Import JSON",
                    () -> jsonImportService.importFromJson(selectedFile.toPath()),
                    count -> {
                        statusLabel.setText("");
                        Dialogs.info("Import finished", "Imported books: " + count);
                        SelectionBus.INSTANCE.fireBooksChanged();
                        confirmButton.getScene().getWindow().hide();
                    }
            );
            return;
        }

        // GOOGLE_BOOKS
        String q = googleQueryField.getText() == null ? "" : googleQueryField.getText().trim();
        if (q.isEmpty()) {
            statusLabel.setText("Query is required.");
            return;
        }

        int max = maxResultsSpinner.getValue();
        boolean onlyBooks = onlyBooksCheckBox.isSelected();

        confirmButton.setDisable(true);
        statusLabel.setText("Importing from Google Books...");

        runAsync("Import Google Books",
                () -> {
                    // Jeśli Twoja metoda zwraca liczbę zaimportowanych rekordów, zwróć ją tutaj
                    // np. return googleBooksImportService.importByQuery(q, max, onlyBooks);
                    // Na razie 0, dopóki nie podłączysz implementacji:
                    // googleBooksImportService.importByQuery(q, max, onlyBooks);
                    return 0;
                },
                count -> {
                    statusLabel.setText("");
                    Dialogs.info("Import finished", "Imported books: " + count);
                    SelectionBus.INSTANCE.fireBooksChanged();
                    confirmButton.getScene().getWindow().hide();
                }
        );
    }

    private <T> void runAsync(String opName, Callable<T> work, Consumer<T> onSuccess) {
        Task<T> task = new Task<>() {
            @Override
            protected T call() throws Exception {
                return work.call();
            }
        };

        task.setOnSucceeded(e -> Platform.runLater(() -> {
            onSuccess.accept(task.getValue());
            updateConfirmEnabled(); // w razie gdy okno nie zostało zamknięte
        }));

        task.setOnFailed(e -> Platform.runLater(() -> {
            Throwable ex = task.getException();
            log.error("{} failed", opName, ex);
            statusLabel.setText("Import failed: " + (ex == null ? "" : ex.getMessage()));
            updateConfirmEnabled();
        }));

        Thread t = new Thread(task, "fx-" + opName.replace(' ', '-').toLowerCase());
        t.setDaemon(true);
        t.start();
    }

    @FXML
    private void onCancel() {
        confirmButton.getScene().getWindow().hide();
    }
}
