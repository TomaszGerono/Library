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
import com.tg.library.persistence.AuthorVO;

/**
 * 
 * @author tomasz
 */
public class Finder {
    
    
            /**
             * Queries the DB for all authors which match given arguments. 
             * If the user left a text field blank Swing's getText() method returns an
             * empty String which then you pass as an argument and the if statements
             * make sure the SQL query does not contain an empty String as a value argument.
             * 
             * @throws SQLException 
             * @return List<Author> matching criteria
             */
    public List<Author> queryForAuthor(String firstName, String middleName, String lastName, String title, String monastery) {

        
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
    }
    
}
