package com.tg.library.businessrules;

import com.tg.library.LibraryApplication;
import com.tg.library.entity.Authors;
import com.tg.library.entity.Books;
import com.tg.library.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = LibraryApplication.class)
@ActiveProfiles("test")
@Transactional
class BookServiceBusinessRulesTest {

    @Autowired
    private BookService bookService;

    @Test
    void shouldAllowBookWithoutAuthors() {
        Books b = new Books();
        b.setTitle("No Author Book");
        b.setAuthors(List.of());

        Books saved = bookService.add(b);

        assertNotNull(saved.getId());
        assertTrue(saved.getAuthors() == null || saved.getAuthors().isEmpty());
    }

    @Test
    void shouldAllowBookWithManyAuthors() {
        Authors a1 = new Authors();
        a1.setFirstName("Robert");
        a1.setLastName("Martin");

        Authors a2 = new Authors();
        a2.setFirstName("Joshua");
        a2.setLastName("Bloch");

        Books b = new Books();
        b.setTitle("Multi-author book");
        b.setAuthors(List.of(a1, a2));

        Books saved = bookService.add(b);

        assertNotNull(saved.getId());
        assertNotNull(saved.getAuthors());
        assertEquals(2, saved.getAuthors().size());
    }
}


