package com.tg.library.gui.view;

public enum GroupBy {
    GENRE("Genre"),
    AUTHOR("Author"),
    SERIES("Series");

    private final String label;

    GroupBy(String label) { this.label = label; }

    @Override public String toString() { return label; }
}
