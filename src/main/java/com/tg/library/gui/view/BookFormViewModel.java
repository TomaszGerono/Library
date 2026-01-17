package com.tg.library.gui.view;

import com.tg.library.entity.Books;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BookFormViewModel {

    public final StringProperty title = new SimpleStringProperty("");
    public final StringProperty author = new SimpleStringProperty("");
    public final StringProperty genre = new SimpleStringProperty("");
    public final StringProperty year = new SimpleStringProperty("");
    public final StringProperty isbn = new SimpleStringProperty("");
    public final StringProperty pages = new SimpleStringProperty("");
    public final StringProperty series = new SimpleStringProperty("");
    public final StringProperty monastery = new SimpleStringProperty("");

    public void fromBook(Books b) {
        if (b == null) return;
        title.set(nullToEmpty(b.getTitle()));
        // TODO uncomment
//        author.set(nullToEmpty(b.getAuthor()));
//        genre.set(nullToEmpty(b.getGenre()));
//        year.set(b.getPublicationYear() == null ? "" : b.getPublicationYear().toString());
//        isbn.set(nullToEmpty(b.getIsbn()));
//        pages.set(b.getPages() == null ? "" : b.getPages().toString());
//        series.set(nullToEmpty(b.getSeries()));
    }

    public void applyTo(Books b) {

        b.setTitle(trimToNull(title.get()));
        // TODO uncomment
        //b.setAuthor(trimToNull(author.get()));
        //b.setGenre(trimToNull(genre.get()));
        b.setIsbn(trimToNull(isbn.get()));
        //b.setSeries(trimToNull(series.get()));

        b.setPublicationYear(parseIntOrNull(year.get()));
        //b.setPages(parseIntOrNull(pages.get()));
    }

    public String validate() {
        if (title.get().trim().isEmpty()) return "Title field is required.";
        if (!year.get().isBlank() && parseIntOrNull(year.get()) == null) return "Year must be a number.";
        if (!pages.get().isBlank() && parseIntOrNull(pages.get()) == null) return "Pages must be a number.";
        return null;
    }

    private Integer parseIntOrNull(String s) {
        if (s == null) return null;
        String t = s.trim();
        if (t.isEmpty()) return null;
        try { return Integer.parseInt(t); }
        catch (NumberFormatException e) { return null; }
    }

    private String nullToEmpty(String s) { return s == null ? "" : s; }
    private String trimToNull(String s) {
        if (s == null) return null;
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }

    public void clear() {
        title.set("");
        author.set("");
        year.set("");
        isbn.set("");
        pages.set("");
        series.set("");
    }

}

