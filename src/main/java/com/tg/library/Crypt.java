package com.tg.library;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.security.SecureRandom;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 *
 * @author Tomasz
 */
public class Crypt {
  
    public static String hashPassword(String password) {
        
//        https://www.baeldung.com/java-password-hashing
//        https://www.javaspring.net/blog/messagedigest-in-java/

        try {
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            var md = MessageDigest.getInstance("SHA - 512");
            md.update(salt);
            byte[] inputBytes = password.getBytes();
            md.update(inputBytes);
            byte[] hash = md.digest();
            String encodedHash = Base64.getEncoder().encodeToString(hash);
            return encodedHash;
        }
        
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        
        return null;
    }
}
