package com.tg.library.gui.controller;

import com.tg.library.AppContext;
import com.tg.library.entity.Books;
import com.tg.library.entity.Progress;
import com.tg.library.gui.view.ShelvesViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.springframework.stereotype.Component;

@Component
public class ShelvesController {
    private AppContext ctx;
    private ShelvesViewModel shelvesViewModel;

//    @FXML private ListView<Section> shelvesList;
    @FXML private Button renameBtn;
    @FXML private Button deleteBtn;

    @FXML private TableView<Books> booksTable;
    @FXML private TableColumn<Books, String> titleCol;
    @FXML private TableColumn<Books, String> authorCol;
    @FXML private TableColumn<Books, Progress> statusCol;

    @FXML private Button addBookBtn;
    @FXML private Button removeBookBtn;

    public void setContext(AppContext ctx) { this.ctx = ctx; }

    public void afterContextInjected() {
//        this.shelvesViewModel = new ShelvesViewModel(ctx.shelves(), ctx.books());
//        shelvesViewModel.load();
//
//        shelvesList.setItems(shelvesViewModel.shelves());
//        shelvesList.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> {
//            shelvesViewModel.setSelectedShelf(n);
//            renameBtn.setDisable(n == null);
//            deleteBtn.setDisable(n == null);
//            addBookBtn.setDisable(n == null);
//        });
//
//        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
//        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
//        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
//
//        booksTable.setItems(shelvesViewModel.shelfBooks());
//        booksTable.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> {
//            shelvesViewModel.setSelectedBook(n);
//            removeBookBtn.setDisable(n == null || shelvesViewModel.selectedShelfProperty().get() == null);
//        });
//
//        renameBtn.setDisable(true);
//        deleteBtn.setDisable(true);
//        addBookBtn.setDisable(true);
//        removeBookBtn.setDisable(true);
    }

    @FXML
    public void onAddShelf() {
//        Optional<String> name = Dialogs.promptText("Nowa półka", "Podaj nazwę półki:");
//        name.map(String::trim).filter(s -> !s.isBlank()).ifPresent(n -> {
//            try { vm.addShelf(n); }
//            catch (Exception e) { Dialogs.error("Nie udało się dodać półki", e.getMessage()); }
//        });
    }

    @FXML
    public void onRenameShelf() {
//        Shelf s = vm.selectedShelfProperty().get();
//        if (s == null) return;
//
//        Optional<String> name = Dialogs.promptText("Zmień nazwę", "Nowa nazwa półki:", s.getName());
//        name.map(String::trim).filter(x -> !x.isBlank()).ifPresent(n -> {
//            try { vm.renameShelf(s.getId(), n); }
//            catch (Exception e) { Dialogs.error("Nie udało się zmienić nazwy", e.getMessage()); }
//        });
    }

    @FXML
    public void onDeleteShelf() {
//        Shelf s = vm.selectedShelfProperty().get();
//        if (s == null) return;
//
//        boolean ok = Dialogs.confirm("Usunąć półkę?", "Czy na pewno usunąć półkę: " + s.getName() + " ?");
//        if (!ok) return;
//
//        try { vm.deleteShelf(s.getId()); }
//        catch (Exception e) { Dialogs.error("Nie udało się usunąć półki", e.getMessage()); }
    }

    @FXML
    public void onAddBookToShelf() {
//        Shelf s = vm.selectedShelfProperty().get();
//        if (s == null) return;
//
//        // prosta lista wyboru (w produkcji: dialog z wyszukiwaniem)
//        ChoiceDialog<Book> dialog = new ChoiceDialog<>();
//        dialog.setTitle("Dodaj książkę");
//        dialog.setHeaderText("Wybierz książkę do dodania na półkę: " + s.getName());
//        dialog.getItems().setAll(vm.allBooks());
//        dialog.setConverter(new javafx.util.StringConverter<>() {
//            @Override public String toString(Book b) { return b == null ? "" : b.getTitle() + " — " + b.getAuthor(); }
//            @Override public Book fromString(String string) { return null; }
//        });
//
//        dialog.showAndWait().ifPresent(b -> {
//            try { vm.addBookToShelf(s.getId(), b.getId()); }
//            catch (Exception e) { Dialogs.error("Nie udało się dodać książki", e.getMessage()); }
//        });
    }

    @FXML
    public void onRemoveBookFromShelf() {
//        Shelf s = vm.selectedShelfProperty().get();
//        Book b = vm.selectedBookProperty().get();
//        if (s == null || b == null) return;
//
//        try { vm.removeBookFromShelf(s.getId(), b.getId()); }
//        catch (Exception e) { Dialogs.error("Nie udało się usunąć książki z półki", e.getMessage()); }
    }
}

