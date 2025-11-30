package com.tg.library;

import java.awt.EventQueue;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.tg.library.gui.LoginWindow;


/**
 *
 * @author tomasz
 */
public class Library {
    
    private static String url = "jdbc:sqlite:./sqlite/db/library.db";

    public static void main(String[] args) {
        // Start the GUI on EDT
        EventQueue.invokeLater(() -> {
            new LoginWindow().setVisible(true);
        });
        
        // Run the DB connection in a separate thread
//        new Thread(() -> connect(url)).start();
    }
    
    public static void startGUI() {
            EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginWindow();
            }
        });
    }
    
    public static void connect(String url) {

        try (var conn = DriverManager.getConnection(url)) {
            System.out.println("Connection to SQLite has been established.");
            
            if (conn != null) {
                var meta = conn.getMetaData();
                System.out.println("The driver name is: " + meta.getDriverName());
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static String getUrl() {
        return url;
    }
    
}
