/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tg.library.persistence;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author tomasz
 */
public class Saver {
    
        public static void saveUser(String login, String passwordHash) {
        
        
        LocalDateTime currentDateTime = LocalDateTime.now();
        String stringCurrentDateTime = currentDateTime.toString();
        
        ArrayList<Object> values = new ArrayList<>();
        values.add(login);
        values.add(passwordHash);
        values.add(stringCurrentDateTime);
        values.add(Integer.valueOf(1));
        
        
        String url = "jdbc:sqlite:./sqlite/db/users.db";
        String sql = "INSERT INTO users(login, passwordHash, dateCreated, isActive) VALUES (?,?,?,?)";
        
        try {
            var conn = DriverManager.getConnection(url);
            var pstat = conn.prepareStatement(sql);
            pstat.setString(1, values.get(0).toString());
            pstat.setString(2, values.get(1).toString());
            pstat.setString(3, values.get(2).toString());
            pstat.setInt(4, (int) values.get(3));
            pstat.executeUpdate();
            System.out.println("User registered!");
        }
        
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    
    
    }

}