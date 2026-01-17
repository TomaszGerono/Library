package com.tg.library.gui.view;

import com.tg.library.service.BookService;
import com.tg.library.service.TopicService;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.tg.library.entity.Progress; // or wherever your enum is located

public class OverviewViewModel {
    private final BookService bookService;
    private final TopicService topicService;

    public final IntegerProperty totalBooks = new SimpleIntegerProperty(0);
    public final IntegerProperty totalTopics = new SimpleIntegerProperty(0);
    public final IntegerProperty unread = new SimpleIntegerProperty(0);
    public final IntegerProperty reading = new SimpleIntegerProperty(0);
    public final IntegerProperty completed = new SimpleIntegerProperty(0);

    public final ObservableList<String> recommendations = FXCollections.observableArrayList();

    public OverviewViewModel(BookService bookService, TopicService topicService) {
        this.bookService = bookService;
        this.topicService = topicService;
    }

    public void load() {
//        var books = bookService.findAll();
//        totalBooks.set(books.size());
//        totalTopics.set(topicService.findAll().size());
//
//        unread.set((int) books.stream().filter(b -> b.getStatus() == Progress.unread).count());
//        reading.set((int) books.stream().filter(b -> b.getStatus() == Progress.reading).count());
//        completed.set((int) books.stream().filter(b -> b.getStatus() == Progress.completed).count());

    }
}
