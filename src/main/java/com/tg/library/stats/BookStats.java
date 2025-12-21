package com.tg.library.stats;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;

import com.tg.library.Config;

/**
 *
 * @author tomasz
 */
public class BookStats {
    
    
    /**
     * Counts the number of books that the database contains
     * @return int
     */
    public static int countBooksInLibrary() {
        
        int count = 0;

        
        try (var conn = DriverManager.getConnection(Config.get_library_db_url())) {
            
        
            var sql = "SELECT COUNT(*) as count_books FROM books";
            
            var pstmt = conn.prepareStatement(sql.toString());
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) { count = rs.getInt("count_books"); }
            
            
        }
        
        catch (SQLException e) { e.printStackTrace(); }
        
        return count;
        
    }
    
    
}
