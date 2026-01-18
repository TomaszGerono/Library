package com.tg.library.gui.view;

import com.tg.library.entity.Books;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public enum SelectionBus {
    INSTANCE;

    private final ObjectProperty<Books> selectedBook = new SimpleObjectProperty<>();

    private final javafx.beans.property.BooleanProperty booksChanged =
            new javafx.beans.property.SimpleBooleanProperty(false);

    public javafx.beans.property.BooleanProperty booksChangedProperty() {
        return booksChanged;
    }

    public void fireBooksChanged() {
        booksChanged.set(!booksChanged.get()); // toggle, żeby zawsze wywołać listener
    }

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

