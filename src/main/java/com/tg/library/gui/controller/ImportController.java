package com.tg.library.gui.controller;

import com.tg.library.service.GoogleBooksImportService;
import com.tg.library.service.JsonImportService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class ImportController {

    enum Source { JSON_FILE, GOOGLE_BOOKS }

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

    private final GoogleBooksImportService googleBooksImportService;
    private final JsonImportService jsonImportService;

    @Autowired
    public ImportController(JsonImportService jsonImportService, GoogleBooksImportService googleBooksImportService) {
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

        // walidacja dla google query
        googleQueryField.textProperty().addListener((obs, o, n) -> updateConfirmEnabled());
    }

    private void setPaneVisible(Region pane, boolean visible) {
        pane.setVisible(visible);
        pane.setManaged(visible);
    }

    private void updateConfirmEnabled() {
        Source src = sourceChoiceBox.getValue();
        boolean enabled = switch (src) {
            case JSON_FILE -> selectedFile != null;
            case GOOGLE_BOOKS -> googleQueryField.getText() != null && !googleQueryField.getText().trim().isEmpty();
        };
        confirmButton.setDisable(!enabled);
    }

    @FXML
    private void onSelectFile() {
        FileChooser fc = new FileChooser();
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

        try {
            Source src = sourceChoiceBox.getValue();
            if (src == Source.JSON_FILE) {
                // jsonImportService.importFromJson(selectedFile.toPath());
                // TODO: wywołaj swój import
            } else {
                String q = googleQueryField.getText().trim();
                int max = maxResultsSpinner.getValue();
                boolean onlyBooks = onlyBooksCheckBox.isSelected();

                // googleBooksImportService.importByQuery(q, max, onlyBooks);
                // TODO: wywołaj swój import z API
            }

            // zamknij okno po sukcesie
            confirmButton.getScene().getWindow().hide();

        } catch (Exception e) {
            statusLabel.setText("Import failed: " + e.getMessage());
        }
    }

    @FXML
    private void onCancel() {
        confirmButton.getScene().getWindow().hide();
    }
}
