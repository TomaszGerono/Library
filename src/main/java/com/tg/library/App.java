package com.tg.library;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class App extends Application {

    private ConfigurableApplicationContext context;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
        context = new SpringApplicationBuilder(LibraryApplication.class).headless(false).run(getParameters().getRaw().toArray(new String[0]));
    }

    @Override
    public void start(Stage stage) throws Exception {
//        AppContext ctx = new AppContext();
//
//        FXMLLoader loader = new FXMLLoader(App.class.getResource("/com/tg/library/gui/main-shell.fxml"));
//        loader.setControllerFactory(type -> {
//            try {
//                Object controller = type.getDeclaredConstructor().newInstance();
//                // proste wstrzyknięcie kontekstu jeśli kontroler ma setContext
//                try {
//                    type.getMethod("setContext", AppContext.class).invoke(controller, ctx);
//                } catch (NoSuchMethodException ignored) {
//                }
//                return controller;
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        });
//
//        Scene scene = new Scene(loader.load(), 1200, 720);
//        scene.getStylesheets().add(App.class.getResource("/styles/app.css").toExternalForm());
//        stage.setTitle("Library Manager");
//        stage.setScene(scene);
//        stage.show();
//
//        BookService bookService = context.getBean(BookService.class);
//        System.out.println(bookService.findAll().size());
//        bookService.findAll().forEach(books -> {
//            System.out.println(books.toString());
//        });

        stage.getIcons().add(new Image(App.class.getResourceAsStream("/icons/icon.png")));
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/com/tg/library/gui/main-shell.fxml"));

        loader.setControllerFactory(context::getBean);

        Scene scene = new Scene(loader.load(), 1200, 720);
        scene.getStylesheets().add(App.class.getResource("/styles/app.css").toExternalForm());

        stage.setTitle("Library Manager");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        if (context != null) context.close();
    }

}
