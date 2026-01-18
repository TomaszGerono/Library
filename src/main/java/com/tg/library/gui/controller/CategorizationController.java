package com.tg.library.gui.controller;

import com.tg.library.entity.Books;
import com.tg.library.entity.Genres;
import com.tg.library.entity.Progress;
import com.tg.library.gui.view.GroupBy;
import com.tg.library.service.BookService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import static com.tg.library.gui.util.AuthorsFormatter.formatAuthors;

@Component
public class CategorizationController {

    private final BookService bookService;

    public CategorizationController(BookService bookService) {
        this.bookService = bookService;
    }

    @FXML
    private ComboBox<GroupBy> groupByCombo;
    @FXML private TextField groupSearchField;
    @FXML private Label groupsCountLabel;
    @FXML private ListView<String> groupsList;

    @FXML private Label selectedGroupLabel;
    @FXML private TableView<Books> booksTable;
    @FXML private TableColumn<Books, String> titleCol;
    @FXML private TableColumn<Books, String> authorCol;
    @FXML private TableColumn<Books, String> genreCol;
    @FXML private TableColumn<Books, String> seriesCol;
    @FXML private TableColumn<Books, Integer> yearCol;
    @FXML private TableColumn<Books, Progress> statusCol;

    private final List<Books> masterBooks = new ArrayList<>();
   // private FilteredList<String> filteredGroups;

    private final javafx.collections.ObservableList<String> groupsSource =
            javafx.collections.FXCollections.observableArrayList();

    private final javafx.collections.transformation.FilteredList<String> filteredGroups =
            new javafx.collections.transformation.FilteredList<>(groupsSource, s -> true);


    @FXML
    public void initialize() {
        // combo
        groupByCombo.setItems(FXCollections.observableArrayList(GroupBy.values()));
        groupByCombo.getSelectionModel().select(GroupBy.GENRE);

        // groups list
        //filteredGroups = new FilteredList<>(FXCollections.observableArrayList(), s -> true);

        groupsList.setItems(filteredGroups);

        // tabela
        titleCol.setCellValueFactory(cd -> new javafx.beans.property.SimpleStringProperty(safe(cd.getValue().getTitle())));
        authorCol.setCellValueFactory(cd -> new javafx.beans.property.SimpleStringProperty(
                formatAuthors(cd.getValue().getAuthors())
        ));
        genreCol.setCellValueFactory(cd -> {
            Genres g = cd.getValue().getGenre();
            return new javafx.beans.property.SimpleStringProperty(g == null ? "" : safe(g.getName()));
        });
        seriesCol.setCellValueFactory(cd -> new javafx.beans.property.SimpleStringProperty(
                cd.getValue().getSerie() == null ? "" : String.valueOf(cd.getValue().getSerie().getSeriesName())
        ));
        yearCol.setCellValueFactory(cd -> new javafx.beans.property.SimpleObjectProperty<>(cd.getValue().getPublicationYear()));
        statusCol.setCellValueFactory(cd -> new javafx.beans.property.SimpleObjectProperty<>(cd.getValue().getReadingProgress()));

        // zmiana typu grupowania
        groupByCombo.valueProperty().addListener((obs, o, n) -> rebuildGroups());

        // filtr grup
        groupSearchField.textProperty().addListener((obs, o, n) -> applyGroupFilter(n));

        // wybór grupy -> pokaż książki
        groupsList.getSelectionModel().selectedItemProperty().addListener((obs, o, groupKey) -> {
            if (groupKey == null) {
                selectedGroupLabel.setText("Pick group on the left");
                booksTable.getItems().clear();
                return;
            }
            selectedGroupLabel.setText("Group: " + groupKey);
            showBooksForGroup(groupKey);
        });

        // load
        onRefresh();
    }

    @FXML
    public void onRefresh() {
        Task<List<Books>> task = new Task<>() {
            @Override protected List<Books> call() {
                return bookService.findAll();
            }
        };

        task.setOnSucceeded(e -> {
            masterBooks.clear();
            if (task.getValue() != null) {
                masterBooks.addAll(task.getValue().stream().filter(Objects::nonNull).toList());
            }
            rebuildGroups();
        });

        task.setOnFailed(e -> showError("Refresh failed", task.getException()));

        Thread t = new Thread(task, "fx-categorization-refresh");
        t.setDaemon(true);
        t.start();
    }

    private void rebuildGroups() {
        GroupBy mode = groupByCombo.getValue();
        if (mode == null) mode = GroupBy.GENRE;

        String prev = groupsList.getSelectionModel().getSelectedItem();

        Set<String> groups = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);

        switch (mode) {
            case GENRE -> {
                for (Books b : masterBooks) {
                    if (b.getGenre() != null && b.getGenre().getName() != null) {
                        groups.add(b.getGenre().getName().trim());
                    }
                }
            }
            case AUTHOR -> {
                for (Books b : masterBooks) {
                    if (b.getAuthors() == null) continue;
                    b.getAuthors().forEach(a -> {
                        String name = formatAuthors(List.of(a));
                        if (!name.isBlank()) groups.add(name);
                    });
                }
            }
            case SERIES -> {
                for (Books b : masterBooks) {
                    if (b.getSerie() != null) groups.add(b.getSerie().getSeriesName());
                    else groups.add("(no series)");
                }
            }
        }

        groupsSource.setAll(groups);
        applyGroupFilter(groupSearchField.getText());
        groupsCountLabel.setText("Groups: " + filteredGroups.size());

        Platform.runLater(() -> {
            if (prev != null && filteredGroups.contains(prev)) {
                groupsList.getSelectionModel().select(prev);
            } else if (!filteredGroups.isEmpty()) {
                groupsList.getSelectionModel().selectFirst();
            } else {
                booksTable.getItems().clear();
                selectedGroupLabel.setText("Pick group on the left");
            }
        });
    }

    private void showBooksForGroup(String groupKey) {
        GroupBy mode = groupByCombo.getValue();
        if (mode == null) mode = GroupBy.GENRE;

        List<Books> result;

        switch (mode) {
            case GENRE -> result = masterBooks.stream()
                    .filter(b -> b.getGenre() != null && safe(b.getGenre().getName()).equalsIgnoreCase(groupKey))
                    .toList();

            case AUTHOR -> result = masterBooks.stream()
                    .filter(b -> b.getAuthors() != null && b.getAuthors().stream().anyMatch(a -> {
                        String name = formatAuthors(List.of(a));
                        return !name.isBlank() && name.equalsIgnoreCase(groupKey);
                    }))
                    .toList();

            case SERIES -> {
                if ("(no series)".equals(groupKey)) {
                    result = masterBooks.stream().filter(b -> b.getSerie() == null).toList();
                } else {
                    Integer id = parseSeriesId(groupKey);
                    result = masterBooks.stream()
                            .filter(b -> java.util.Optional.ofNullable(b.getSerie())
                                    .map(s -> s.getSeriesName())
                                    .filter(name -> name.equals(groupKey))
                                    .isPresent()
                            )
                            .toList();
                }
            }

            default -> result = List.of();
        }

        booksTable.setItems(FXCollections.observableArrayList(result));
    }

    private Integer parseSeriesId(String key) {
        // "Series 12" -> 12
        try {
            String t = key.replace("Series", "").trim();
            return Integer.parseInt(t);
        } catch (Exception e) {
            return null;
        }
    }

    private static String safe(String s) { return s == null ? "" : s.trim(); }

    private void showError(String title, Throwable ex) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(title);
        a.setHeaderText(title);
        a.setContentText(ex == null ? "" : ex.getMessage());
        a.showAndWait();
    }

    private void applyGroupFilter(String text) {
        String query = text == null ? "" : text.trim().toLowerCase();

        filteredGroups.setPredicate(group -> {
            if (query.isBlank()) return true;
            return group != null && group.toLowerCase().contains(query);
        });

        groupsCountLabel.setText("Groups: " + filteredGroups.size());
    }

}
