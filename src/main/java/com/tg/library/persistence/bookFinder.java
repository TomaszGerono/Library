/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tg.library.persistence;

import com.tg.library.Library;
import java.sql.*;
import com.tg.library.objects.Book;
import java.util.List;
import java.util.LinkedList;


/**
 *
 * @author Tomasz
 */
public class bookFinder {
    
    private Statement statement;
    
        public List<Book> selectBooks() {
            
        
            List<Book> books = new LinkedList<Book>();
            
            try {
                
                ResultSet result = statement.executeQuery("SELECT * FROM books");
                int id;
                String title;
                int authorID;
                
                while (result.next()) {
                    id = result.getInt("book_id");
                    title = result.getString("title");
                    authorID = result.getInt("author_id");
                    books.add(new Book(id, title, authorID));
                }
                
                System.out.println(books);
            }
            
            catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
            
            return books;
        
        }

    }

