package com.tg.library.gui.controller;

import com.tg.library.entity.Books;
import com.tg.library.entity.Genres;
import com.tg.library.entity.Progress;
import com.tg.library.gui.util.AuthorsFormatter;
import com.tg.library.gui.view.BooksViewModel;
import com.tg.library.gui.view.SelectionBus;
import com.tg.library.service.AuthorService;
import com.tg.library.service.BookService;
import com.tg.library.service.GenresService;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Log4j2
@Component
public class BooksController {

    private static final Logger LOG = LogManager.getLogger(BooksController.class);

    private static final PseudoClass UNREAD_PC = PseudoClass.getPseudoClass("unread");
    private static final PseudoClass READING_PC = PseudoClass.getPseudoClass("reading");
    private static final PseudoClass COMPLETED_PC = PseudoClass.getPseudoClass("completed");

    private final BookService bookService;
    private final GenresService genresService;
    private final AuthorService authorsService;
    private final BooksViewModel vm = new BooksViewModel();
    @FXML
    private TextField titleFilter;
    @FXML
    private TextField authorFilter;
    @FXML
    private ComboBox<Genres> genreFilter;
    @FXML
    private TextField yearFilter;
    @FXML
    private TableView<Books> booksTable;
    @FXML
    private TableColumn<Books, String> titleCol;
    @FXML
    private TableColumn<Books, String> authorCol;
    @FXML
    private TableColumn<Books, String> genreCol;
    @FXML
    private TableColumn<Books, Integer> yearCol;
    @FXML
    private TableColumn<Books, String> isbnCol;
    @FXML
    private TableColumn<Books, Progress> statusCol;
    @FXML
    private TableColumn<Books, String> monasteryCol;
    @FXML
    private TableColumn<Books, String> seriesCol;

    @FXML
    private Button editBtn;
    @FXML
    private Button removeBtn;
    @FXML
    private Label resultCountLabel;

    @Autowired
    public BooksController(BookService bookService, GenresService genresService, AuthorService authorsService) {
        this.bookService = bookService;
        this.genresService = genresService;
        this.authorsService = authorsService;
    }

    private static String nullSafe(String s) {
        return s == null ? "" : s;
    }

    private static <S> void enableTooltipForColumn(TableColumn<S, String> col) {
        col.setCellFactory(c -> new TableCell<>() {
            private final Tooltip tooltip = new Tooltip();

            {
                setTextOverrun(OverrunStyle.ELLIPSIS);
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.isBlank()) {
                    setText(null);
                    setTooltip(null);
                    return;
                }

                setText(item);
                tooltip.setText(item);
                setTooltip(tooltip);
            }
        });
    }

    @FXML
    public void initialize() {

        titleCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTitle()));
        authorCol.setCellValueFactory(cd ->
                new javafx.beans.property.SimpleStringProperty(AuthorsFormatter.formatAuthors(cd.getValue().getAuthors()))
        );
        genreCol.setCellValueFactory(data -> {
            Genres g = data.getValue().getGenre();
            String name = (g == null) ? "" : nullSafe(g.getName());
            return new javafx.beans.property.SimpleStringProperty(name);
        });

        yearCol.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getPublicationYear()));
        isbnCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(nullSafe(data.getValue().getIsbn())));
        statusCol.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getReadingProgress()));
        monasteryCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(nullSafe(data.getValue().getMonastery())));
        seriesCol.setCellValueFactory(data ->
                new SimpleStringProperty(
                        Optional.ofNullable(data.getValue().getSerie())
                                .map(s -> s.getSeriesName())
                                .orElse("")
                )
        );
        enableTooltipForColumn(titleCol);
        enableTooltipForColumn(authorCol);
        enableTooltipForColumn(isbnCol);
        enableTooltipForColumn(monasteryCol);
        enableTooltipForColumn(seriesCol);

        booksTable.setItems(vm.booksView());
        vm.booksView().comparatorProperty().bind(booksTable.comparatorProperty());

        booksTable.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> {
            vm.setSelectedBook(n);
            editBtn.setDisable(n == null);
            removeBtn.setDisable(n == null);
        });

        editBtn.setDisable(true);
        removeBtn.setDisable(true);

        // filtry
        titleFilter.textProperty().bindBidirectional(vm.titleFilterProperty());
        authorFilter.textProperty().bindBidirectional(vm.authorFilterProperty());
        yearFilter.textProperty().bindBidirectional(vm.yearFilterProperty());

//        statusFilter.setItems(FXCollections.observableArrayList(ReadingStatus.values()));
//        statusFilter.valueProperty().bindBidirectional(vm.statusFilterProperty());

        // genres zasilamy po load
        genreFilter.valueProperty().bindBidirectional(vm.genreFilterProperty());

        resultCountLabel.textProperty().bind(vm.filteredCountProperty().asString("Results: %d"));

        booksTable.setRowFactory(tv -> {
            TableRow<Books> row = new TableRow<>() {
                @Override
                protected void updateItem(Books book, boolean empty) {
                    super.updateItem(book, empty);

                    // reset pseudo-klas
                    pseudoClassStateChanged(UNREAD_PC, false);
                    pseudoClassStateChanged(READING_PC, false);
                    pseudoClassStateChanged(COMPLETED_PC, false);

                    if (empty || book == null || book.getReadingProgress() == null) {
                        return;
                    }

                    switch (book.getReadingProgress()) {
                        case unread -> pseudoClassStateChanged(UNREAD_PC, true);
                        case reading -> pseudoClassStateChanged(READING_PC, true);
                        case completed -> pseudoClassStateChanged(COMPLETED_PC, true);
                    }
                }
            };

            row.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2 && !row.isEmpty()) {
                    onEdit();
                }
            });

            return row;
        });

        loadAsync();

        genreFilter.setConverter(new javafx.util.StringConverter<Genres>() {
            @Override
            public String toString(Genres g) {
                return (g == null) ? "" : nullSafe(g.getName());
            }

            @Override
            public Genres fromString(String s) {
                return null;
            }
        });

        genreFilter.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Genres g, boolean empty) {
                super.updateItem(g, empty);
                setText(empty || g == null ? "" : nullSafe(g.getName()));
            }
        });

        booksTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, old, selected) -> {
                    SelectionBus.INSTANCE.setSelectedBook(selected);
                });

        SelectionBus.INSTANCE.booksChangedProperty().addListener((obs, o, n) -> {
            booksTable.refresh();
        });

        SelectionBus.INSTANCE.booksChangedProperty().addListener((obs, oldV, newV) -> {
            loadAsync();
        });
        LOG.info("BooksController initialized");
    }

    @FXML
    public void onClearFilters() {
        vm.clearFilters();
//        refreshGenreChoices();
    }

    @FXML
    public void onAdd() {
        var genres = genresService.findAll();
        var authors = authorsService.findAll();
        var result = BookFormDialogController.showDialog(Dialogs.ownerWindow(), null, genres, authors);
        if (result == null) return;
        //BookFormDialogController.Result r = BookFormDialogController.showDialog(Dialogs.ownerWindow(), null, genres);
        if (result == null || result.book() == null) return;

        runAsync("Adding book",
                () -> bookService.add(result.book()),
                this::loadAsync);

    }

    @FXML
    public void onEdit() {

        Books selected = booksTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        // dane do comboboxów/list (genre + autorzy)
        var genres = genresService.findAll();
        var authors = authorsService.findAll(); // jeśli masz wybór autorów w dialogu

        // OTWÓRZ dialog w trybie edycji: przekazujesz selected
        var result = BookFormDialogController.showDialog(
                Dialogs.ownerWindow(),
                selected,
                genres,
                authors
        );

        if (result == null || result.book() == null) return;

        runAsync("Updating book",
                () -> bookService.update(result.book()),
                this::loadAsync);

//        BookDTO selected = vm.getSelectedBook();
//        if (selected == null) return;
//
//        BookFormDialogController.Result r = BookFormDialogController.showDialog(Dialogs.ownerWindow(), selected);
//        if (r == null || r.book() == null) return;
//
//        runAsync("Zapis zmian", () -> {
//            bookService.update(r.book());
//            return null;
//        }, this::loadAsync);
    }

    @FXML
    public void onRemove() {
        Books selected = booksTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        boolean ok = Dialogs.confirm(
                "Delete book ?",
                "Are you sure you want to delete: " + selected.getTitle() + "?"
        );
        if (!ok) return;

        Long id = selected.getId();
        if (id == null) {
            Dialogs.error("Error", "Cannot remove book without ID.");
            return;
        }

        runAsync("Removing book",
                () -> bookService.deleteById(id),
                this::loadAsync);
    }

    private void loadAsync() {

        runAsync("Loading books", () -> bookService.findAll(), books -> {
            System.out.println(">>> 2. Querying Database..."); // Debug check
            var safe = books == null ? java.util.List.<Books>of()
                    : books.stream().filter(java.util.Objects::nonNull).toList();

            System.out.println(">>> 3. Database returned: " + safe.size() + " books.");
            log.info("TTT Loaded={} nonNull={}", books == null ? 0 : books.size(), safe.size());

            vm.setAll(safe);
            Platform.runLater(() -> {
                if (!booksTable.getItems().isEmpty()) {
                    booksTable.getSelectionModel().selectFirst();
                }
            });
            refreshGenreChoices();
        });


    }

    private void refreshGenreChoices() {
        // unikalne gatunki z aktualnie załadowanych książek
        java.util.Map<Long, Genres> byId = new java.util.LinkedHashMap<>();
        for (Books b : vm.booksView()) { // albo master list w VM, jeśli masz
            Genres g = b.getGenre();
            if (g != null && g.getId() != null) byId.putIfAbsent(g.getId(), g);
        }

        javafx.collections.ObservableList<Genres> items = javafx.collections.FXCollections.observableArrayList();
        items.add(null);                  // null = "brak filtra"
        items.addAll(byId.values());

        genreFilter.setItems(items);
    }


    private <T> void runAsync(String opName, java.util.concurrent.Callable<T> work, java.util.function.Consumer<T> onSuccess) {
        Task<T> task = new Task<>() {
            @Override
            protected T call() throws Exception {
                return work.call();
            }
        };
        task.setOnSucceeded(e -> {
            onSuccess.accept(task.getValue());
            if (!booksTable.getItems().isEmpty()) {
                booksTable.getSelectionModel().selectFirst();
            }
            SelectionBus.INSTANCE.fireBooksChanged();
        });
        task.setOnFailed(e -> Dialogs.error(opName + " nieudane", task.getException().getMessage()));

        //System.out.println(task.getException().getStackTrace());
        Thread t = new Thread(task, "fx-" + opName.replace(' ', '-').toLowerCase());
        t.setDaemon(true);
        t.start();
    }

    private void runAsync(String opName, Runnable work, Runnable onSuccess) {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                work.run();
                return null;
            }
        };
        task.setOnSucceeded(e -> onSuccess.run());
        // task.setOnFailed(...)
//        task.setOnSucceeded(e -> {
//            log.info("{} succeeded", opName);
//            onSuccess.accept(task.getValue());
//        });
        task.setOnFailed(e -> log.error("{} failed", opName, task.getException()));

        Thread t = new Thread(task, "fx-" + opName.replace(' ', '-').toLowerCase());
        t.setDaemon(true);
        t.start();
    }

}

