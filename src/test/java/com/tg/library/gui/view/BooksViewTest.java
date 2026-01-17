package com.tg.library.gui.view;

import com.tg.library.entity.Authors;
import com.tg.library.entity.Books;
import com.tg.library.entity.Genres;
import com.tg.library.gui.controller.BooksController;
import com.tg.library.service.AuthorService;
import com.tg.library.service.BookService;
import com.tg.library.service.GenresService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationTest;

import java.net.URL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BooksViewTest extends ApplicationTest {

    private BookService bookServiceMock;
    private GenresService genresServiceMock;
    private AuthorService authorServiceMock;
    private BooksController controller;

    @Override
    public void start(Stage stage) throws Exception {

        // mock BookService
        bookServiceMock = Mockito.mock(BookService.class);

        when(bookServiceMock.findAll()).thenReturn(List.of(
                Books.builder().title("Clean Code").authors(List.of(Authors.builder().firstName("Robert").lastName("Martin").build())).genre(Genres.builder().id(1L).name("non-fiction").build()).isbn("1234").publicationYear(2008).pagesCount(120).build(),
                Books.builder().title("Effective Java").authors(List.of(Authors.builder().firstName("Joshua").lastName("Bloch").build())).genre(Genres.builder().id(1L).name("non-fiction").build()).isbn("1234").publicationYear(2018).pagesCount(264).build(),
                Books.builder().title("Clean Coder").authors(List.of(Authors.builder().firstName("Robert").lastName("Martin").build())).genre(Genres.builder().id(1L).name("non-fiction").build()).isbn("1234").publicationYear(2011).pagesCount(167).build()
        ));

        // TODO add genresService - dane
        genresServiceMock = Mockito.mock(GenresService.class);
        authorServiceMock = Mockito.mock(AuthorService.class);

        // 2️⃣ kontroler z mockiem
        controller = new BooksController(bookServiceMock, genresServiceMock, authorServiceMock);

        // 3️⃣ załaduj FXML z kontrolerem
        URL fxml = getClass().getResource("/com/tg/library/gui/books/books-view.fxml");
        assertNotNull(fxml, "books-view.fxml not found");

        FXMLLoader loader = new FXMLLoader(fxml);
        loader.setControllerFactory(type -> {
            if (type == BooksController.class) return controller;
            try {
                return type.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });

        Parent root = loader.load();
        stage.setScene(new Scene(root, 1000, 650));
        stage.show();
    }

    @Test
    void shouldLoadBooks() {
        TableView<?> table = lookup("#booksTable").queryAs(TableView.class);

        waitUntil(() -> table.getItems().size() == 3);

        assertEquals(3, table.getItems().size());
        verify(bookServiceMock, times(1)).findAll();
    }

    @Test
    void shouldFilterByTitle() {
        TableView<?> table = lookup("#booksTable").queryAs(TableView.class);
        waitUntil(() -> table.getItems().size() == 3);

        clickOn("#titleFilter").write("Clean");
        //sleep(5000);

        assertEquals(2, table.getItems().size());
        assertTrue(((Books) table.getItems().get(0)).getTitle().contains("Clean"));
    }

//    @Test
//    void shouldFilterByAuthor() {
//        TableView<?> table = lookup("#booksTable").queryAs(TableView.class);
//        waitUntil(() -> table.getItems().size() == 3);
//
//        clickOn("#authorFilter").write("Martin");
//        sleep(300);
//
//        assertEquals(1, table.getItems().size());
//    }

    // ---------- helper ----------

    private void waitUntil(BooleanSupplier cond) {
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < 4000) {
            if (cond.getAsBoolean()) return;
            //sleep(5000);
        }
        fail("Condition not met in time");
    }

    private interface BooleanSupplier {
        boolean getAsBoolean();
    }
}
