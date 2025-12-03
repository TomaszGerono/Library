package com.tg.library;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author tomasz
 */
public class Config {
    
    private static final String users_db_url = "jdbc:sqlite:./sqlite/db/users.db";
    private static final String library_db_url = "jdbc:sqlite:./sqlite/db/library.db";
    private static final String icon_filepath = "/icon.png";
    
    public static String get_library_db_url() { return library_db_url; }
    
    public static String get_users_db_url() {return users_db_url;}
    
    public static String get_icon_filepath() {return icon_filepath; }
    
}
