package com.tg.library.gui.controller;

import com.tg.library.App;
import com.tg.library.AppContext;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.fxml.FXML;


import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;


@Component
public class MainShellController {
    private final AppContext ctx;
//    private Image importIcon = stage.getIcons().add(new Image(App.class.getResourceAsStream("/icons/import-icon.png")));

    @Autowired
    public MainShellController(AppContext ctx) {
        this.ctx = ctx;
    }

    @FXML
    public void onExport() {

//        private Image exportIcon = stage.getIcons().add(new Image(App.class.getResourceAsStream("/icons/export-icon.png")));


        try {
            var fxmlPath = "/com/tg/library/gui/export/export.fxml";
            var loader = new FXMLLoader(MainShellController.class.getResource(fxmlPath));

            // If using Spring, you MUST set the controller factory
            loader.setControllerFactory(ctx.getSpringContext()::getBean);


            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Export Configuration");
            stage.initModality(Modality.APPLICATION_MODAL); // Blocks the main window until closed
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onImport() {
        try {
            var fxmlPath = "/com/tg/library/gui/import/import.fxml";
            var loader = new FXMLLoader(MainShellController.class.getResource(fxmlPath));

            loader.setControllerFactory(ctx.getSpringContext()::getBean);

            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Import Configuration");
            stage.initModality(Modality.APPLICATION_MODAL); // Blocks the main window until closed
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }





    }
