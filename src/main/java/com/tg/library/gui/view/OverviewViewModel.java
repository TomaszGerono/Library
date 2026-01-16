package com.tg.library.gui.view;

import com.tg.library.service.BookService;
import com.tg.library.service.ShelfService;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OverviewViewModel {
    private final BookService bookService;
    private final ShelfService shelfService;

    public final IntegerProperty totalBooks = new SimpleIntegerProperty(0);
    public final IntegerProperty totalShelves = new SimpleIntegerProperty(0);
    public final IntegerProperty unread = new SimpleIntegerProperty(0);
    public final IntegerProperty reading = new SimpleIntegerProperty(0);
    public final IntegerProperty completed = new SimpleIntegerProperty(0);

    public final ObservableList<String> recommendations = FXCollections.observableArrayList();

    public OverviewViewModel(BookService bookService, ShelfService shelfService) {
        this.bookService = bookService;
        this.shelfService = shelfService;
    }

    public void load() {
        var books = bookService.findAll();
        totalBooks.set(books.size());
        // TODO uncomment
        //totalShelves.set(shelfService.findAll().size());

//        unread.set((int) books.stream().filter(b -> b.getStatus() == ReadingStatus.UNREAD).count());
//        reading.set((int) books.stream().filter(b -> b.getStatus() == ReadingStatus.READING).count());
//        completed.set((int) books.stream().filter(b -> b.getStatus() == ReadingStatus.COMPLETED).count());

    }
}
