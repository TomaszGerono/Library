package com.tg.library.gui.controller;

import com.tg.library.entity.Books;
import com.tg.library.entity.Progress;
import com.tg.library.entity.Topics;
import com.tg.library.gui.util.AuthorsFormatter;
import com.tg.library.gui.view.SelectionBus;
import com.tg.library.service.BookService;
import com.tg.library.service.TopicService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
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

        removeBookBtn.setDisable(true);
        booksTable.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> {
            removeBookBtn.setDisable(n == null);
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

    @FXML
    private void onAddTopic() {
        TextInputDialog d = new TextInputDialog();
        d.setTitle("Add topic");
        d.setHeaderText("Create new topic");
        d.setContentText("Topic name:");

        Button ok = (Button) d.getDialogPane().lookupButton(ButtonType.OK);
        ok.setDisable(true);
        d.getEditor().textProperty().addListener((obs, o, n) -> {
            ok.setDisable(n == null || n.trim().isEmpty());
        });

        var result = d.showAndWait();
        if (result.isEmpty()) return;

        String name = result.get().trim();
        if (name.isEmpty()) return;

        createTopicAsync(name);
    }

    @FXML
    private void onDeleteTopic() {
        Topics selected = topicsList.getSelectionModel().getSelectedItem();
        if (selected == null || selected.getId() == null) return;

        int booksInTopic = booksTable.getItems() == null ? 0 : booksTable.getItems().size();

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Remove topic");
        confirm.setHeaderText("Do you want to remove this topic?");
        confirm.setContentText(
                "Topic: " + safe(selected.getName()) +
                        (booksInTopic > 0 ? ("\nBooks linked: " + booksInTopic + "\nLinks will be removed.") : "")
        );

        var res = confirm.showAndWait();
        if (res.isEmpty() || res.get() != ButtonType.OK) return;

        deleteTopicAsync(selected.getId());
    }

    @FXML
    private void onAddBookToTopic() {
        Topics topic = topicsList.getSelectionModel().getSelectedItem();
        if (topic == null || topic.getId() == null) return;

        // dialog
        Dialog<Books> dialog = new Dialog<>();
        dialog.setTitle("Add book to topic");
        dialog.setHeaderText("Select a book to add to topic: " + safe(topic.getName()));

        ButtonType addType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL, addType);

        TextField filter = new TextField();
        filter.setPromptText("Filter by title...");

        ListView<Books> list = new ListView<>();
        list.setPrefHeight(320);

        // ładne renderowanie książek
        list.setCellFactory(lv -> new ListCell<>() {
            @Override protected void updateItem(Books b, boolean empty) {
                super.updateItem(b, empty);
                if (empty || b == null) { setText(null); return; }
                String year = b.getPublicationYear() == null ? "" : " (" + b.getPublicationYear() + ")";
                setText(safe(b.getTitle()) + year);
            }
        });

        VBox content = new VBox(10, filter, list);
        content.setPrefWidth(520);
        dialog.getDialogPane().setContent(content);

        // disable Add gdy nic nie zaznaczone
        Button addBtn = (Button) dialog.getDialogPane().lookupButton(addType);
        addBtn.setDisable(true);

        list.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> addBtn.setDisable(n == null));

        // załaduj listę książek do wyboru (bez tych, które już są w topicu)
        loadBooksForPickerAsync(topic.getId(), list, filter);

        dialog.setResultConverter(bt -> bt == addType ? list.getSelectionModel().getSelectedItem() : null);

        var res = dialog.showAndWait();
        if (res.isEmpty() || res.get() == null) return;

        Books chosen = res.get();
        addBookToTopicAsync(chosen.getId(), topic.getId());
    }

    private static String safe(String s) { return s == null ? "" : s; }

    private void showError(String title, Throwable ex) {
        log.error(ex);
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(title);
        a.setHeaderText(title);
        a.setContentText(ex == null ? "" : ex.getMessage());
        a.showAndWait();
    }

    private void createTopicAsync(String name) {
        Task<Topics> task = new Task<>() {
            @Override protected Topics call() {
                return topicsService.add(new Topics().builder().name(name).build());
            }
        };

        task.setOnSucceeded(e -> {
            Topics created = task.getValue();
            // odśwież listę i zaznacz nowy topic
            loadTopicsAsyncAndSelect(created == null ? null : created.getId());
            SelectionBus.INSTANCE.fireBooksChanged(); // opcjonalnie: jeśli overview/liczniki zależą od topics
        });

        task.setOnFailed(e -> showError("Add topic failed", task.getException()));

        Thread t = new Thread(task, "fx-create-topic");
        t.setDaemon(true);
        t.start();
    }

    private void loadTopicsAsyncAndSelect(Long topicIdToSelect) {
        Task<List<Topics>> task = new Task<>() {
            @Override protected List<Topics> call() {
                return topicsService.findAll();
            }
        };

        task.setOnSucceeded(e -> {
            List<Topics> list = task.getValue() == null ? List.of()
                    : task.getValue().stream().filter(Objects::nonNull).toList();

            topicsList.setItems(FXCollections.observableArrayList(list));

            Platform.runLater(() -> {
                if (topicsList.getItems().isEmpty()) return;

                if (topicIdToSelect == null) {
                    topicsList.getSelectionModel().selectFirst();
                    return;
                }

                for (int i = 0; i < topicsList.getItems().size(); i++) {
                    Topics t = topicsList.getItems().get(i);
                    if (t != null && t.getId() != null && t.getId().equals(topicIdToSelect)) {
                        topicsList.getSelectionModel().select(i);
                        topicsList.scrollTo(i);
                        return;
                    }
                }

                // fallback
                topicsList.getSelectionModel().selectFirst();
            });
        });

        task.setOnFailed(e -> showError("Loading topics failed", task.getException()));

        Thread t = new Thread(task, "fx-reload-topics");
        t.setDaemon(true);
        t.start();
    }

    private void deleteTopicAsync(Long topicId) {
        Task<Void> task = new Task<>() {
            @Override protected Void call() {
                // wybierz jedną z metod w serwisie:
                topicsService.deleteTopic(topicId); // jeśli robisz deleteLinks + deleteById
                // topicService.deleteById(topicId); // jeśli wystarcza
                return null;
            }
        };

        task.setOnSucceeded(e -> {
            booksTable.getItems().clear(); // wyczyść prawą tabelę
            loadTopicsAsyncAndSelectFirst(); // odśwież listę
            SelectionBus.INSTANCE.fireBooksChanged(); // opcjonalnie (overview/liczniki)
        });

        task.setOnFailed(e -> showError("Remove topic failed", task.getException()));

        Thread t = new Thread(task, "fx-delete-topic");
        t.setDaemon(true);
        t.start();
    }

    private void loadTopicsAsyncAndSelectFirst() {
        Task<List<Topics>> task = new Task<>() {
            @Override protected List<Topics> call() {
                return topicsService.findAll();
            }
        };

        task.setOnSucceeded(e -> {
            var list = task.getValue() == null ? List.<Topics>of()
                    : task.getValue().stream().filter(Objects::nonNull).toList();

            topicsList.setItems(FXCollections.observableArrayList(list));

            Platform.runLater(() -> {
                if (!topicsList.getItems().isEmpty()) {
                    topicsList.getSelectionModel().selectFirst();
                }
            });
        });

        task.setOnFailed(e -> showError("Loading topics failed", task.getException()));

        Thread t = new Thread(task, "fx-reload-topics");
        t.setDaemon(true);
        t.start();
    }

    private void loadBooksForPickerAsync(Long topicId, ListView<Books> list, TextField filter) {
        Task<List<Books>> task = new Task<>() {
            @Override protected List<Books> call() {
                // 1) wszystkie książki
                List<Books> all = bookService.findAll(); // potrzebujesz wstrzykniętego BookService
                // 2) książki już w topicu
                List<Books> inTopic = topicsService.findBooksByTopicId(topicId);

                java.util.Set<Long> inIds = inTopic.stream()
                        .map(Books::getId)
                        .filter(java.util.Objects::nonNull)
                        .collect(java.util.stream.Collectors.toSet());

                return all.stream()
                        .filter(b -> b != null && b.getId() != null)
                        .filter(b -> !inIds.contains(b.getId())) // pokaż tylko te, których jeszcze nie ma
                        .toList();
            }
        };

        task.setOnSucceeded(e -> {
            var items = javafx.collections.FXCollections.observableArrayList(task.getValue());
            var filtered = new javafx.collections.transformation.FilteredList<>(items, b -> true);

            filter.textProperty().addListener((obs, o, n) -> {
                String q = (n == null) ? "" : n.trim().toLowerCase();
                filtered.setPredicate(b -> q.isBlank() || safe(b.getTitle()).toLowerCase().contains(q));
            });

            list.setItems(filtered);
        });

        task.setOnFailed(e -> showError("Loading books failed", task.getException()));

        Thread t = new Thread(task, "fx-load-books-picker");
        t.setDaemon(true);
        t.start();
    }

    private void addBookToTopicAsync(Long bookId, Long topicId) {
        Task<Void> task = new Task<>() {
            @Override protected Void call() {
                bookService.addBookToTopic(bookId, topicId);
                return null;
            }
        };

        task.setOnSucceeded(e -> {
            loadBooksForTopicAsync(topicId);
            SelectionBus.INSTANCE.fireBooksChanged();
        });

        task.setOnFailed(e -> showError("Add book failed", task.getException()));

        Thread t = new Thread(task, "fx-add-book-to-topic");
        t.setDaemon(true);
        t.start();
    }

    @FXML
    private void onRemoveBookFromTopic() {
        Topics topic = topicsList.getSelectionModel().getSelectedItem();
        Books book = booksTable.getSelectionModel().getSelectedItem();

        if (topic == null || topic.getId() == null) return;
        if (book == null || book.getId() == null) return;

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Remove book from topic");
        confirm.setHeaderText("Do you want to remove the book from this topic?");
        confirm.setContentText("Topic: " + safe(topic.getName()) + "\nBook: " + safe(book.getTitle()));

        var res = confirm.showAndWait();
        if (res.isEmpty() || res.get() != ButtonType.OK) return;

        removeBookFromTopicAsync(book.getId(), topic.getId());
    }

    private void removeBookFromTopicAsync(Long bookId, Long topicId) {
        Task<Void> task = new Task<>() {
            @Override protected Void call() {
                topicsService.removeBookFromTopic(bookId, topicId);
                return null;
            }
        };

        task.setOnSucceeded(e -> {
            // odśwież prawą tabelę
            loadBooksForTopicAsync(topicId);

            // opcjonalnie: powiadom inne widoki (overview)
            SelectionBus.INSTANCE.fireBooksChanged();
        });

        task.setOnFailed(e -> showError("Remove book failed", task.getException()));

        Thread t = new Thread(task, "fx-remove-book-from-topic");
        t.setDaemon(true);
        t.start();
    }


}
