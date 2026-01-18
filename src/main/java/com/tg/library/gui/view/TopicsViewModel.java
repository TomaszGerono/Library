package com.tg.library.gui.view;

import com.tg.library.entity.Books;
import com.tg.library.entity.Topics;
import com.tg.library.service.BookService;
import com.tg.library.service.TopicService;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class TopicsViewModel {

    private final TopicService topicService;
    private final BookService bookService;

    private final ObservableList<Topics> topics = FXCollections.observableArrayList();
    private final ObservableList<Books> topicBooks = FXCollections.observableArrayList();

    private final ObjectProperty<Topics> selectedTopic = new SimpleObjectProperty<>();
    private final ObjectProperty<Books> selectedBook = new SimpleObjectProperty<>();

    @Autowired
    public TopicsViewModel(TopicService topicService, BookService bookService) {
        this.topicService = topicService;
        this.bookService = bookService;
    }

    public void load() {
        topics.setAll(topicService.findAll());
        if (selectedTopic.get() != null) loadBooksForSelectedTopic();
        else topicBooks.clear();
    }

    public ObservableList<Topics> topics() { return topics; }
    public ObservableList<Books> topicBooks() { return topicBooks; }

    public ObjectProperty<Topics> selectedTopicProperty() { return selectedTopic; }
    public ObjectProperty<Books> selectedBookProperty() { return selectedBook; }

    public void setSelectedTopic(Topics t) {
        selectedTopic.set(t);
        loadBooksForSelectedTopic();
    }

    public void setSelectedBook(Books b) { selectedBook.set(b); }

    public Topics addTopic(String name) {
        var t = topicService.create(name);
        return t;
    }

    public void renameTopic(Long id, String newName) {
//        topicService.rename(id, newName);
        load();
    }

    public void deleteTopic(Long id) {
//        topicService.deleteById(id);
//        selectedTopic.set(null);
        load();
    }

    public void addBookToTopic(Long topicId, Long bookId) {
//        topicService.addBookToTopic(topicId, bookId);
//        loadBooksForSelectedTopic();
    }

    public void removeBookFromTopic(Long topicId, Long bookId) {
        // TODO uncomment
//        topicService.removeBookFromTopic(topicId, bookId);
        loadBooksForSelectedTopic();
    }

    public Set<Books> allBooks() {
        return new HashSet<>(bookService.findAll());
    }

    private void loadBooksForSelectedTopic() {
        Topics t = selectedTopic.get();
        if (t == null) {
            topicBooks.clear();
            return;
        }
// TODO uncomment
//        var books = topicService.findBooksInTopic(t.getTopicId());
// TODO uncomment
//        topicBooks.setAll(books);
    }
}