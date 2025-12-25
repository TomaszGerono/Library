package com.tg.library.persistence;

import java.util.List;
import java.lang.StringBuilder;
import java.util.ArrayList;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.tg.library.Config;
import com.tg.library.objects.Author;
import com.tg.library.objects.User;
import com.tg.library.objects.Book;
import com.tg.library.persistence.AuthorVO;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author tomasz
 */
public class Finder {
   
    /**
     * Queries the DB for all authors which match given arguments. 
     * If the user left a text field blank Swing's getText() method returns an
     * empty String which then is passed as an argument and the if statements
     * make sure the SQL query does not contain an empty String as a value argument.
     * 
     * @throws SQLException 
     * @return List<Author> matching criteria
     */
    public List<Author> queryForAuthors(String firstName, String middleName, String lastName, String title, String monastery) {

        
//        Create a class that contains nothing but data members (value object pattern.) Use wrapper classes like Integer, Double and String. 
//        If if there is a value then the data item is not null. That is the query object. Construct a string based on the items that are set. Populate using the same items.
//        https://forums.oracle.com/ords/apexds/post/optional-parameter-in-preparedstatement-6657
    
          var authorVO = new AuthorVO();  // author value object
          authorVO.setFirstName(firstName);
          authorVO.setMiddleName(middleName);
          authorVO.setLastName(lastName);
          authorVO.setTitle(title);
          authorVO.setMonastery(monastery);
             
            
            var sql = new StringBuilder("SELECT * FROM authors WHERE 1=1");
            List<Object> params = new ArrayList<>();
            
            if (authorVO.getFirstName() != null && !authorVO.getFirstName().isBlank()) {
                sql.append(" and first_name LIKE ?");
                params.add("%" + authorVO.getFirstName().trim() + "%");
            }
            
            if (authorVO.getMiddleName() != null && !authorVO.getMiddleName().isBlank()) {
            sql.append(" and middle_name LIKE ?");
            params.add("%" + authorVO.getMiddleName().trim() + "%");
            }
            
            if (authorVO.getLastName() != null && !authorVO.getLastName().isBlank()) {
            sql.append(" and last_name LIKE ?");
            params.add("%" + authorVO.getFirstName().trim() + "%");
            }
            
            if (authorVO.getTitle() != null && !authorVO.getTitle().isBlank()) {
            sql.append(" and title LIKE ?");
            params.add("%" + authorVO.getTitle().trim() + "%");
            }
            
            if (authorVO.getMonastery() != null && !authorVO.getMonastery().isBlank()) {
            sql.append(" and monastery LIKE ?");
            params.add("%" + authorVO.getMonastery().trim() + "%");
            }
            
            
            List<Author> results = new ArrayList<>();
            
            try (var conn = DriverManager.getConnection(Config.get_library_db_url())) {
                
                var pstmt = conn.prepareStatement(sql.toString());
                
                for (int i = 0; i < params.size(); i++) {  pstmt.setObject(i+1, params.get(i));  }
                
                ResultSet rs = pstmt.executeQuery();
                
                while (rs.next()) {
                    var author = new Author();
                    author.setFirstName(rs.getString("first_name"));
                    author.setMiddleName(rs.getString("middle_name"));
                    author.setLastName(rs.getString("last_name"));
                    author.setTitle(rs.getString("title"));
                    author.setMonastery(rs.getString("monastery"));
                    
                    results.add(author);
                }
                
                return results;
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
            return null;
    }
    
    
    
    /**
     * Check if user exists by checking if login exists in database.
     * @param String login
     * @throws SQLException
     * @return boolean result
     */
    public static boolean userExists(String login) {
        
        try (var conn = DriverManager.getConnection(Config.get_users_db_url())) {
        
            var sql = "SELECT * FROM users WHERE login LIKE ? ";
            
            var pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, login);
            
            ResultSet rs = pstmt.executeQuery();
            
            List<User> results = new ArrayList<>();
            
            while (rs.next()) {
                String resultLogin = rs.getString("login");
                var user = new User();
                user.setLogin(resultLogin);
                results.add(user);
            }
            
            for (User user : results) { 
                if (user.getLogin().equals(login)) {
                    return true;
                }
            }
            
            return false;
        }
        
        catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
        
    }
    

    /**
     * Queries the database for all books matching given parameters.
     * Minimum 1 parameter, rest optional.
     * @return List<Book> containing objects matching criteria
     * @throws SQLException
     */
    public static List<Book> queryForBooks(String title, String alternateTitle, String genreID, String sectionID, String mainCharacter, String seriesID, String topicID ) { 
    
        var bookVO = new BookVO();
        bookVO.setTitle(title);
        bookVO.setAlternateTitle(alternateTitle);
        bookVO.setGenreID(genreID);
        bookVO.setSectionID(sectionID);
        bookVO.setMainCharacter(mainCharacter);
        bookVO.setSeriesID(seriesID);
        bookVO.setTopicID(topicID);
        
        var sql = new StringBuilder("SELECT * FROM books WHERE 1=1");
        List<Object> params = new ArrayList<>();
        
        if (bookVO.getTitle() != null && !bookVO.getTitle().isBlank()) {
            sql.append(" and title LIKE ?");
            params.add("%" + bookVO.getTitle().trim() + "%");
        }
        
        if (bookVO.getAlternateTitle() != null && !bookVO.getAlternateTitle().isBlank()) {
            sql.append(" and alternate_title LIKE ?");
            params.add("%" + bookVO.getAlternateTitle().trim() + "%");
        }
        
        if (bookVO.getGenreID() != null && !bookVO.getGenreID().isBlank()) {
            sql.append(" and genre_id LIKE ?");
            params.add("%" + bookVO.getGenreID().trim() + "%");
        }
        
        if (bookVO.getSectionID() != null && !bookVO.getSectionID().isBlank()) {
            sql.append(" and section_id LIKE ?");
            params.add("%" + bookVO.getSectionID().trim() + "%");
        }
        
        if (bookVO.getMainCharacter() != null && !bookVO.getMainCharacter().isBlank()) {
            sql.append(" and main_character LIKE ?");
            params.add("%" + bookVO.getMainCharacter().trim() + "%");
        }
        
        if (bookVO.getSeriesID() != null && !bookVO.getSeriesID().isBlank()) {
            sql.append(" and series_id LIKE ?");
            params.add("%" + bookVO.getSeriesID().trim() + "%");
        }
        
        if (bookVO.getSeriesID() != null && !bookVO.getTopicID().isBlank()) {
            sql.append(" and topic_id LIKE ?");
            params.add("%" + bookVO.getTopicID().trim() + "%");
        }
        
        List<Book> results = new ArrayList<>();
        
        try (var conn = DriverManager.getConnection(Config.get_library_db_url())) {
            var pstmt = conn.prepareStatement(sql.toString());

            for (int i = 0; i < params.size(); i++) { pstmt.setObject(i+1, params.get(i)); }
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                var book = new Book();
                book.setTitle(rs.getString("title"));
                book.setAlternateTitle(rs.getString("alternate_title"));
                book.setGenreID( rs.getInt("genre_id"));
                book.setSectionID( rs.getInt("section_id") );
                book.setMainCharacter( rs.getString("main_character") );
                book.setSeriesID(  rs.getInt("series_id"));
                book.setTopicID( rs.getInt( "topic_id" ));
                
                results.add(book);
            }
            return results;
        }
        
        catch (SQLException e) { 
        
            System.out.println("Something went wrong!"); // todo: create a popup
            
            e.printStackTrace(); 
        
        }
        
        return null;
        
    };
    
    
//    public static List<Publisher> queryForPublishers(String name, String country, String region, String city, String street, String streetNumber, String houseNumber) {}
    
    
    
}
