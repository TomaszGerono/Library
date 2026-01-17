package com.tg.library.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Paths;

@Component
public class ImportController {

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
        fc.setTitle("Select file to import");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );

        // Get the current window from the text field
        targetFile = fc.showOpenDialog(pathDisplayField.getScene().getWindow());

        if (targetFile != null) {
            pathDisplayField.setText(targetFile.getAbsolutePath());
            confirmButton.setDisable(false); // Enable the 'Import Now' button
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
     * Logic to import from CSV to sqlite
     */
    @FXML
    public void onConfirmImport() {
        var projectDir = System.getProperty("user.dir");
        var dbPath = Paths.get(projectDir, "sqlite", "db", "library.db").toAbsolutePath().toString();

        String sqliteExe;

        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            // Assume you have sqlite3.exe in your project or PATH
            sqliteExe = "sqlite3";
        } else {
            sqliteExe = "sqlite3"; // Standard on Linux
        }

        try {
            // Start sqlite3 interactive shell on the DB
            ProcessBuilder pb = new ProcessBuilder(sqliteExe, dbPath);
            pb.redirectErrorStream(true); // merge error output so we can see it
            Process process = pb.start();

            // "Type" the commands into sqlite3 safely
            try (PrintWriter writer = new PrintWriter(process.getOutputStream())) {
                // Set mode to CSV
                writer.println(".mode csv");

                // Convert backslashes to forward slashes becase SQLite might complain
                String safePath = targetFile.getAbsolutePath().replace("\\", "/");

                // Run the import command (target table is 'books')
                // --skip 1 ignores the header row if your CSV has one
                writer.println(".import \"" + safePath + "\" books");

                writer.println(".exit");
            }

            // wait for finish
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Import Successful!");
                onCancel();
            } else {
                // if fails, read output
                String output = new String(process.getInputStream().readAllBytes());
                System.err.println("Import Failed. SQLite Output:\n" + output);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}