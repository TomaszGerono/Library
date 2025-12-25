package com.tg.library.persistence;

import java.sql.DriverManager;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

import com.tg.library.Config;

/**
 *
 * @author tomasz
 */
public class passwdComparer {
    
    /**
     * Compares passwords
     * @param inputLogin
     * @param plaintextPasswd
     * @return boolean
     */
    public static boolean comparePasswords(String inputLogin, String plaintextPasswd) {
    
            try (var conn = DriverManager.getConnection(Config.get_users_db_url())) {
                        String sql = String.format("SELECT passwordHash FROM users WHERE '%s'=login;", inputLogin);
                        var pstat = conn.prepareStatement(sql);
                        var rs = pstat.executeQuery();
                        String passwordHash = "";
                        
                        while (rs.next()) {
                            passwordHash = rs.getString("passwordHash");
                        }
                        
                        return BCrypt.checkpw(plaintextPasswd, passwordHash);
            }
            
           catch (SQLException e) {
               e.printStackTrace();
           } 
            
            return false;
            
    }
    
           
    
}
