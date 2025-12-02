/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tg.library.persistence;

import java.sql.DriverManager;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author tomasz
 */
public class passwdComparer {
    
    public static boolean comparePasswords(String inputLogin, String plaintextPasswd) {
    
            try {
                
                String url = "jdbc:sqlite:./sqlite/db/users.db";
                String sql = String.format("SELECT passwordHash FROM users WHERE '%s'=login;", inputLogin);

                var conn = DriverManager.getConnection(url);
                var pstat = conn.prepareStatement(sql);
                var rs = pstat.executeQuery();
                
                String passwordHash = "";
                
                while(rs.next()) {
                
                    passwordHash = rs.getString("passwordHash");
                
                }
                
                boolean passwordMatches = BCrypt.checkpw(plaintextPasswd, passwordHash);
                
                return passwordMatches;
                
        }
        
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    
        return false;
            
    }    
    
}
