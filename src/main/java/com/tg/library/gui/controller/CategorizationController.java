package com.tg.library.gui.controller;

import com.tg.library.entity.Progress;
import com.tg.library.entity.Books;
import com.tg.library.service.BookService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CategorizationController {

    private final BookService bookService;

    public CategorizationController(BookService bookService) {
        this.bookService = bookService;
    }

    enum GroupBy {
        GENRE("Genre"),
        AUTHOR("Author"),
        SERIES("Series");

        final String label;
        GroupBy(String label) { this.label = label; }
        @Override public String toString() { return label; }
    }

    @FXML private ComboBox<GroupBy> groupByCombo;
    @FXML private TextField groupSearchField;
    @FXML private ListView<String> groupsList;
    @FXML private Label groupsCountLabel;

    @FXML private Label selectedGroupLabel;
    @FXML private TableView<Books> booksTable;
    @FXML private TableColumn<Books, String> titleCol;
    @FXML private TableColumn<Books, String> authorCol;
    @FXML private TableColumn<Books, String> genreCol;
    @FXML private TableColumn<Books, String> seriesCol;
    @FXML private TableColumn<Books, Integer> yearCol;
    @FXML private TableColumn<Books, Progress> statusCol;

    private List<Books> allBooks = List.of();
    private Map<String, List<Books>> grouped = Map.of();

    @FXML
    public void initialize() {
        // Kolumny (dla record/DTO: lambda)
        titleCol.setCellValueFactory(d -> new SimpleStringProperty(nullSafe(d.getValue().getTitle())));
        authorCol.setCellValueFactory(d -> new SimpleStringProperty(nullSafe(d.getValue().getTitle())));
        genreCol.setCellValueFactory(d -> new SimpleStringProperty(nullSafe(d.getValue().getTitle())));
        seriesCol.setCellValueFactory(d -> new SimpleStringProperty(nullSafe(d.getValue().getSeriesId().toString())));
        yearCol.setCellValueFactory(d -> new SimpleObjectProperty<>(d.getValue().getPublicationYear()));
// TODO uncomment
        //        statusCol.setCellValueFactory(d -> new SimpleObjectProperty<>(ReadingStatus.COMPLETED));

        groupByCombo.setItems(FXCollections.observableArrayList(GroupBy.values()));
        groupByCombo.getSelectionModel().select(GroupBy.GENRE);

        groupByCombo.valueProperty().addListener((obs, o, n) -> regroupAndRender());
        groupSearchField.textProperty().addListener((obs, o, n) -> renderGroupsList());

        groupsList.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> {
            if (n == null) {
                selectedGroupLabel.setText("Pick group on the left");
                booksTable.setItems(FXCollections.observableArrayList());
                return;
            }
            selectedGroupLabel.setText("Group: " + n);
            booksTable.setItems(FXCollections.observableArrayList(grouped.getOrDefault(n, List.of())));
        });

        onRefresh();
    }

    @FXML
    public void onRefresh() {
        Task<List<Books>> task = new Task<>() {
            @Override protected List<Books> call() {
                return bookService.findAll(); // dopasuj do swojej metody
            }
        };

        task.setOnSucceeded(e -> {
            allBooks = task.getValue() == null ? List.of() : task.getValue();
            regroupAndRender();
        });

        task.setOnFailed(e -> showError("Refresh failed", task.getException()));

        Thread t = new Thread(task, "fx-categorization-refresh");
        t.setDaemon(true);
        t.start();
    }

    private void regroupAndRender() {
        GroupBy gb = groupByCombo.getValue() == null ? GroupBy.GENRE : groupByCombo.getValue();

        // TODO uncomment !!!
//        Function<Books, String> keyFn = switch (gb) {
//            // TODO usunac toString
//            case GENRE -> b -> normalizeKey(b.getGenreId().toString(), "(brak gatunku)");
//            // TODO uncomment
////            case AUTHOR -> b -> normalizeKey(b.getAlternateTitle(), "(brak autora)");
//            case SERIES -> b -> normalizeKey(b.getSeriesId().toString(), "(brak serii)");
//        };

//        grouped = allBooks.stream()
//                .collect(Collectors.groupingBy(keyFn));

        // sortowanie grup alfabetycznie
        grouped = grouped.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));

        renderGroupsList();

        // reset wyboru po zmianie grupowania
        groupsList.getSelectionModel().clearSelection();
        booksTable.setItems(FXCollections.observableArrayList());
        selectedGroupLabel.setText("Pick group on the left");
    }

    private void renderGroupsList() {
        String q = groupSearchField.getText();
        List<String> keys = new ArrayList<>(grouped.keySet());

        if (q != null && !q.isBlank()) {
            String qq = q.trim().toLowerCase();
            keys = keys.stream()
                    .filter(k -> k.toLowerCase().contains(qq))
                    .toList();
        }

        groupsList.setItems(FXCollections.observableArrayList(keys));
        groupsCountLabel.setText("Number of groups: " + keys.size());
    }

    private String normalizeKey(String value, String fallback) {
        if (value == null) return fallback;
        String t = value.trim();
        return t.isEmpty() ? fallback : t;
    }

    private static String nullSafe(String s) { return s == null ? "" : s; }

    private void showError(String title, Throwable ex) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(title);
        a.setHeaderText(title);
        a.setContentText(ex == null ? "" : ex.getMessage());
        a.showAndWait();
    }
}
