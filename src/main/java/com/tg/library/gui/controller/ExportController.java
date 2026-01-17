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
     * Opens the native file chooser to select where to save the CSV
     */
    @FXML
    public void onSelectFile() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select Export Destination");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv")
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
     * Logic to export from SQLite to CSV
     */
    @FXML
    public void onConfirmExport() {
        if (targetFile == null) return;

        var projectDir = System.getProperty("user.dir");
        var dbPath = Paths.get(projectDir, "sqlite", "db", "library.db").toAbsolutePath().toString();
        String sqliteExe;

        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            // assume sqlite3 is in project or PATH
            sqliteExe = "sqlite3";
        } else {
            sqliteExe = "sqlite3";
        }

        try {
            // build the command
            // sqlite3 -header -csv "path/to/db" "select * from books;" > "path/to/output.csv"
            ProcessBuilder pb = new ProcessBuilder(
                    sqliteExe,
                    "-header",
                    "-csv",
                    dbPath,
                    "SELECT * FROM books;" // Your query
            );

            // redirect output directly to the file the user selected
            pb.redirectOutput(targetFile);

            Process p = pb.start();
            int exitCode = p.waitFor();

            if (exitCode == 0) {
                System.out.println("Export Successful: " + targetFile.getAbsolutePath());
                onCancel();
            } else {
                System.err.println("Export failed with code: " + exitCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}