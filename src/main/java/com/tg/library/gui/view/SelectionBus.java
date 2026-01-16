package com.tg.library.gui.view;

import com.tg.library.entity.Books;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public enum SelectionBus {
    INSTANCE;

    private final ObjectProperty<Books> selectedBook = new SimpleObjectProperty<>();

    public ObjectProperty<Books> selectedBookProperty() {
        return selectedBook;
    }

    public Books getSelectedBook() {
        return selectedBook.get();
    }

    public void setSelectedBook(Books book) {
        selectedBook.set(book);
    }
}

