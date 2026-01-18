package com.tg.library.gui.controller;

import com.tg.library.service.BookService;
import com.tg.library.service.JsonExportService;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Component
public class ExportController {

    BookService bookService;
    JsonExportService jsonExportService;

    @FXML
    private TextField pathDisplayField;
    @FXML
    private Button confirmButton;

    private File targetFile;

    @Autowired
    public ExportController(BookService bookService, JsonExportService jsonExportService) {
        this.bookService = bookService;
        this.jsonExportService = jsonExportService;
    }

    @FXML
    public void onSelectFile() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select Export Destination");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JSON Files", "*.json")
        );

        targetFile = fc.showSaveDialog(pathDisplayField.getScene().getWindow());

        if (targetFile != null) {
            pathDisplayField.setText(targetFile.getAbsolutePath());
            confirmButton.setDisable(false);
        }
    }

    @FXML
    public void onCancel() {
        Stage stage = (Stage) pathDisplayField.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onConfirmExport() throws IOException {

        jsonExportService.exportToJson(bookService.findAll(), targetFile.toPath());
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();

    }
}