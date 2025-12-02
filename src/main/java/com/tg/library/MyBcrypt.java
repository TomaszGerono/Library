package com.tg.library;

import org.mindrot.jbcrypt.BCrypt;

/*
 *
 * @author tomasz
 */
public class MyBcrypt {
    
    public static String hashPassword(String plaintext) {
        
        String salt = BCrypt.gensalt();    
        String bcryptHash = BCrypt.hashpw(plaintext, salt);
        
        return bcryptHash;
    
    }
    
    public static boolean verifyPassword(String plaintext, String hashed) {
        return BCrypt.checkpw(plaintext, hashed);
    }
}