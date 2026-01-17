package com.tg.library.gui.util;

import com.tg.library.entity.Authors;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class AuthorsFormatter {

    public static String formatAuthors(java.util.List<Authors> authors) {
        if (authors == null || authors.isEmpty()) return "";

        // Sklejamy pełne imię i nazwisko dla każdego autora,
        // potem łączymy autorów przecinkami.
        return authors.stream()
                .map(AuthorsFormatter::formatAuthorName)
                .filter(s -> !s.isBlank())
                .distinct()
                .collect(java.util.stream.Collectors.joining(", "));
    }

    public static String formatAuthorName(Authors a) {
        if (a == null) return "";

        String first = safeTrim(a.getFirstName());
        String middle = safeTrim(a.getMiddleName());
        String last = safeTrim(a.getLastName());

        java.util.List<String> parts = new java.util.ArrayList<>(3);
        if (!first.isBlank()) parts.add(first);
        if (!middle.isBlank()) parts.add(middle);
        if (!last.isBlank()) parts.add(last);

        return String.join(" ", parts).trim();
    }

    public static String safeTrim(String s) {
        return s == null ? "" : s.trim();
    }

}
