package com.tg.library.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Component
public class ExportController {

    @FXML
    private TextField pathDisplayField;

    @FXML
    private Button confirmButton;

    private File targetFile;

    /**
     * Opens the native file chooser to select where to save the CSV/JSON
     */
    @FXML
    public void onSelectFile() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select Export Destination");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv"),
                new FileChooser.ExtensionFilter("JSON Files", "*.json")
        );

        // Get the current window from the text field
        targetFile = fc.showSaveDialog(pathDisplayField.getScene().getWindow());

        if (targetFile != null) {
            pathDisplayField.setText(targetFile.getAbsolutePath());
            confirmButton.setDisable(false); // Enable the 'Export Now' button
        }
    }

    /**
     * Closes the subwindow
     */
    @FXML
    public void onCancel() {
        Stage stage = (Stage) pathDisplayField.getScene().getWindow();
        stage.close();
    }

    /**
     * Logic to trigger the Python script using ProcessBuilder
     */
    @FXML
    public void onConfirmExport() {
        if (targetFile == null) return;

        // 1. Determine OS-specific Python path for your .venv
        String os = System.getProperty("os.name").toLowerCase();
        String pythonExec = os.contains("win") ? ".venv/Scripts/python.exe" : ".venv/bin/python";

        // 2. Define paths for your script and database
        // Assuming your DB is at the root in /sqlite/db/library.db
        String dbPath = Paths.get("sqlite", "db", "library.db").toAbsolutePath().toString();
        String scriptPath = Paths.get("src", "main", "resources", "scripts", "export_script.py").toAbsolutePath().toString();

        try {
            ProcessBuilder pb = new ProcessBuilder(
                    pythonExec,
                    scriptPath,
                    dbPath,
                    "books",
                    targetFile.getAbsolutePath()
            );

            // Redirect output to IntelliJ console for debugging
            pb.inheritIO();
            pb.start();

            System.out.println("Python process started for: " + targetFile.getName());

            // Close the window after successfully starting the process
            onCancel();

        } catch (IOException e) {
            System.err.println("Failed to launch Python: " + e.getMessage());
            // In a real app, you would use Dialogs.error() here
        }
    }
}