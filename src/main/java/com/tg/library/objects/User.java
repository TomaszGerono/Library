/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tg.library.objects;

/**
 *
 * @author tomasz
 */
public class User {
    
    private String login;
    private String passwordHash;
    private String dateCreated;
    private int isActive;
    
    public User() {
    
    }
    
    public User(String login, String passwordHash, String dateCreated, int isActive){
    
    }
    
    public User(String login, String passwordHash, String dateCreated, boolean isActive) {
    
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }
    
    
    
}
