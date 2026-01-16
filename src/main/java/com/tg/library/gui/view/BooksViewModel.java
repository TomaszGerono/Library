package com.tg.library.gui.view;

import com.tg.library.entity.Progress;
import com.tg.library.entity.Books;
import com.tg.library.entity.Genres;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import java.util.Set;
import java.util.TreeSet;

import static com.tg.library.gui.util.AuthorsFormatter.formatAuthors;

public class BooksViewModel {

    private final ObservableList<Books> master = FXCollections.observableArrayList();
    private final FilteredList<Books> filtered = new FilteredList<>(master, b -> true);
    private final SortedList<Books> sorted = new SortedList<>(filtered);

    private final ObjectProperty<Books> selectedBook = new SimpleObjectProperty<>();

    private final StringProperty titleFilter = new SimpleStringProperty("");
    private final StringProperty authorFilter = new SimpleStringProperty("");
    private final ObjectProperty<Genres> genreFilter = new SimpleObjectProperty<>(null);
    private final ObjectProperty<Progress> statusFilter = new SimpleObjectProperty<>(null);
    private final StringProperty yearFilter = new SimpleStringProperty("");

    private final ReadOnlyIntegerWrapper filteredCount = new ReadOnlyIntegerWrapper(0);

    public BooksViewModel() {
        InvalidationListener l = obs -> refilter();
        titleFilter.addListener(l);
        authorFilter.addListener(l);
        genreFilter.addListener(l);
        statusFilter.addListener(l);
        yearFilter.addListener(l);
    }

    public void setAll(java.util.List<Books> books) {
        master.setAll(books);
        setSelectedBook(null);
        refilter();
    }

    public SortedList<Books> booksView() { return sorted; }

    public ObjectProperty<Books> selectedBookProperty() { return selectedBook; }
    public Books getSelectedBook() { return selectedBook.get(); }
    public void setSelectedBook(Books b) { selectedBook.set(b); }

    public StringProperty titleFilterProperty() { return titleFilter; }
    public StringProperty authorFilterProperty() { return authorFilter; }
    public ObjectProperty<Genres> genreFilterProperty() { return genreFilter; }
    public ObjectProperty<Progress> statusFilterProperty() { return statusFilter; }
    public StringProperty yearFilterProperty() { return yearFilter; }

    public ReadOnlyIntegerProperty filteredCountProperty() { return filteredCount.getReadOnlyProperty(); }

    public void clearFilters() {
        titleFilter.set("");
        authorFilter.set("");
        genreFilter.set(null);
        statusFilter.set(null);
        yearFilter.set("");
        refilter();
    }

    public Set<Genres> availableGenres() {
        Set<Genres> s = new TreeSet<Genres>();
        for (Books b : master) if (b.getGenre() != null) s.add(b.getGenre());
        return s;
    }

    private void refilter() {
        filtered.setPredicate(book -> {
            if (!containsIgnoreCase(book.getTitle(), titleFilter.get())) return false;
            if (!containsIgnoreCase(formatAuthors(book.getAuthors()), authorFilter.get())) return false;

            Genres gf = genreFilter.get();
            if (gf != null) {
                Genres bg = book.getGenre();
                if (bg == null || bg.getId() == null || gf.getId() == null) return false;
                if (!bg.getId().equals(gf.getId())) return false;
            }

//            ReadingStatus sf = statusFilter.get();
//            if (sf != null && sf != book.status()) return false;
//
            String yf = yearFilter.get();
            if (yf != null && !yf.isBlank()) {
                try {
                    int y = Integer.parseInt(yf.trim());
                    if (book.getPublicationYear() == null || book.getPublicationYear() != y) return false;
                } catch (NumberFormatException e) {
                    // ignore incorrect filter
                }
            }
            return true;
        });
        filteredCount.set(filtered.size());
    }

    private boolean containsIgnoreCase(String value, String query) {
        if (query == null || query.isBlank()) return true;
        if (value == null) return false;
        return value.toLowerCase().contains(query.toLowerCase());
    }

}
