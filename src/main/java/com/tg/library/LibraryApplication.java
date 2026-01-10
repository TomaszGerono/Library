package com.tg.library;

import com.tg.library.repository.AuthorsRepository;
import com.tg.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class LibraryApplication {

//    @Autowired
//    AuthorsRepository authorsRepository;

    public static void main(String[] args) {
//        ConfigurableApplicationContext ctx = new SpringApplicationBuilder(LibraryApplication.class)
//                .headless(false)
//                .run(args);

        SpringContext.init(LibraryApplication.class);
        BookService bookService = SpringContext.getBean(BookService.class);
        System.out.println(bookService.findAll().size());
        bookService.findAll().forEach(books -> { System.out.println(books.toString()); } );
    }

}