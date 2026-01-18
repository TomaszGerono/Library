package com.tg.library.gui.controller;

import com.tg.library.entity.Books;
import com.tg.library.entity.Progress;
import com.tg.library.entity.Topics;
import com.tg.library.gui.util.AuthorsFormatter;
import com.tg.library.service.BookService;
import com.tg.library.service.TopicService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Log4j2
@Component
public class TopicsController {

    private final TopicService topicsService;
    private final BookService bookService;

    @Autowired
    public TopicsController(TopicService topicsService, BookService bookService) {
        this.topicsService = topicsService;
        this.bookService = bookService;
    }

    @FXML private ListView<Topics> topicsList;

    @FXML private TableView<Books> booksTable;
    @FXML private TableColumn<Books, String> titleCol;
    @FXML private TableColumn<Books, String> authorCol;
    @FXML private TableColumn<Books, Progress> statusCol;

    @FXML private Button deleteBtn;
    @FXML private Button addBookBtn;
    @FXML private Button removeBookBtn;

    @FXML
    public void initialize() {
        // jak ma się wyświetlać element w liście (żeby nie był toString)
        topicsList.setCellFactory(lv -> new ListCell<>() {
            @Override protected void updateItem(Topics t, boolean empty) {
                super.updateItem(t, empty);
                setText(empty || t == null ? null : safe(t.getName()));
            }
        });

        // tabela książek
        titleCol.setCellValueFactory(cd -> new javafx.beans.property.SimpleStringProperty(safe(cd.getValue().getTitle())));
        authorCol.setCellValueFactory(cd -> new javafx.beans.property.SimpleStringProperty(
                AuthorsFormatter.formatAuthors(cd.getValue().getAuthors())
        ));
        statusCol.setCellValueFactory(cd -> new javafx.beans.property.SimpleObjectProperty<>(cd.getValue().getReadingProgress()));

        // reaguj na wybór topicu
        topicsList.getSelectionModel().selectedItemProperty().addListener((obs, old, topic) -> {
            if (topic == null) {
                booksTable.getItems().clear();
                setButtonsDisabled(true);
                return;
            }
            setButtonsDisabled(false);
            loadBooksForTopicAsync(topic.getId());
        });

        setButtonsDisabled(true);
        loadTopicsAsync();
    }

    private void setButtonsDisabled(boolean disabled) {
        deleteBtn.setDisable(disabled);
        addBookBtn.setDisable(disabled);
        removeBookBtn.setDisable(disabled);
    }

    private void loadTopicsAsync() {
        Task<List<Topics>> task = new Task<>() {
            @Override protected List<Topics> call() {
                return topicsService.findAll();
            }
        };
        task.setOnSucceeded(e -> {
            var list = task.getValue() == null ? List.<Topics>of() : task.getValue().stream().filter(Objects::nonNull).toList();
            topicsList.setItems(FXCollections.observableArrayList(list));

            // zaznacz pierwszy topic po załadowaniu
            Platform.runLater(() -> {
                if (!topicsList.getItems().isEmpty()) {
                    topicsList.getSelectionModel().selectFirst();
                }
            });
        });
        task.setOnFailed(e -> showError("Loading topics failed", task.getException()));
        Thread t = new Thread(task, "fx-load-topics");
        t.setDaemon(true);
        t.start();
    }

    private void loadBooksForTopicAsync(Long topicId) {
        Task<List<Books>> task = new Task<>() {
            @Override protected List<Books> call() {
                return topicsService.findBooksByTopicId(topicId);
            }
        };
        task.setOnSucceeded(e -> {
            var books = task.getValue() == null ? List.<Books>of() : task.getValue().stream().filter(Objects::nonNull).toList();
            booksTable.setItems(FXCollections.observableArrayList(books));
        });
        task.setOnFailed(e -> showError("Loading books for topic failed", task.getException()));
        Thread t = new Thread(task, "fx-load-topic-books");
        t.setDaemon(true);
        t.start();
    }

    // Handlery – na razie mogą być puste
    @FXML private void onAddTopic() {}
    @FXML private void onDeleteTopic() {}
    @FXML private void onAddBookToTopic() {}
    @FXML private void onRemoveBookFromTopic() {}

    private static String safe(String s) { return s == null ? "" : s; }

    private void showError(String title, Throwable ex) {
        log.error(ex);
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(title);
        a.setHeaderText(title);
        a.setContentText(ex == null ? "" : ex.getMessage());
        a.showAndWait();
    }
}
