package com.tg.library.gui.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Window;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public final class Dialogs {

    private static final ConcurrentHashMap<String, Object> controllerRegistry = new ConcurrentHashMap<>();

    private Dialogs() {
    }

    public static Window ownerWindow() {
        for (Window w : Window.getWindows()) if (w.isShowing()) return w;
        return null;
    }

    public static void info(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.initOwner(ownerWindow());
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    public static void error(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.initOwner(ownerWindow());
        a.setTitle(title);
        a.setHeaderText(title);
        a.setContentText(msg == null ? "" : msg);
        a.showAndWait();
    }

    public static boolean confirm(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.initOwner(ownerWindow());
        a.setTitle(title);
        a.setHeaderText(title);
        a.setContentText(msg);
        return a.showAndWait().filter(b -> b == ButtonType.OK).isPresent();
    }

    public static Optional<String> promptText(String title, String msg) {
        TextInputDialog d = new TextInputDialog();
        d.initOwner(ownerWindow());
        d.setTitle(title);
        d.setHeaderText(title);
        d.setContentText(msg);
        return d.showAndWait();
    }

    public static Optional<String> promptText(String title, String msg, String initial) {
        TextInputDialog d = new TextInputDialog(initial);
        d.initOwner(ownerWindow());
        d.setTitle(title);
        d.setHeaderText(title);
        d.setContentText(msg);
        return d.showAndWait();
    }

    public static void registerController(String fxml, Object controller) {
        controllerRegistry.put(fxml, controller);
    }

    public static Object lookupController(String fxml) {
        return controllerRegistry.get(fxml);
    }
}
