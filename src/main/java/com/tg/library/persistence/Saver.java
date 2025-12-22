package com.tg.library.persistence;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.tg.library.Config;
import com.tg.library.objects.Book;



/**
 *
 * @author tomasz
 */
public class Saver {
    
    
    /**
     * Saves a user to database
     * @param login
     * @param passwordHash
     * @return String 
     */
    public static String saveUser(String login, String passwordHash) {

        LocalDateTime currentDateTime = LocalDateTime.now();
        String stringCurrentDateTime = currentDateTime.toString();

        ArrayList<Object> values = new ArrayList<>();
        values.add(login);
        values.add(passwordHash);
        values.add(stringCurrentDateTime);
        values.add(Integer.valueOf(1));

        String sql = "INSERT INTO users(login, passwordHash, dateCreated, isActive) VALUES (?,?,?,?)";

        try (var conn = DriverManager.getConnection(Config.get_users_db_url())) {
            var pstat = conn.prepareStatement(sql);
            pstat.setString(1, values.get(0).toString()); // SetString sets the value as a literal preventing sql injection
            pstat.setString(2, values.get(1).toString());
            pstat.setString(3, values.get(2).toString());
            pstat.setInt(4, (int) values.get(3));
            pstat.executeUpdate();

            return "success";
        } catch (SQLException e) {
            System.err.println(e.getMessage());

            return "failed";     // Show the user "login already used by someone else"
        }
    }
        
//    public static List<Book> saveBook(String title, String alternateTitle, String genre, String section, String series, String topic) {
//        
//        
//        try (var conn = DriverManager.getConnection(Config.get_library_db_url())) {
//            var pstat = conn.prepareStatement(sql);
//            pstat.executeUpdate();
//
//        } catch (SQLException e) { System.err.println(e.getMessage());  }
//    }

}