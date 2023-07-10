package com.joseneyra.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.util.DigestUtils;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PasswordEncodingTests {

    static final String PASSWORD = "tiger";


    // MD5 Example - Not Recommended
    @Test
    void hashingExample() {
        System.out.println(DigestUtils.md5DigestAsHex(PASSWORD.getBytes()));
        System.out.println(DigestUtils.md5DigestAsHex(PASSWORD.getBytes()));

        String salted = PASSWORD + "ThisIsMySaltValue";
        System.out.println(DigestUtils.md5DigestAsHex(salted.getBytes()));
    }


    // NoOp Example - Not Recommended
    @Test
    void testNoOp() {
        PasswordEncoder noOp = NoOpPasswordEncoder.getInstance();
        System.out.println(noOp.encode(PASSWORD));
    }


    // LDap Example - Not Recommended - Uses Random Salt
    @Test
    void testLDap() {
        PasswordEncoder lDap = new LdapShaPasswordEncoder();
        System.out.println(lDap.encode(PASSWORD));
        System.out.println(lDap.encode(PASSWORD));

        String encodedPassword = lDap.encode(PASSWORD);

        assertTrue(lDap.matches(PASSWORD, encodedPassword));
    }


    // SHA256 Example - Not Recommended - Uses Random Salt
    @Test
    void testLSha256() {
        PasswordEncoder sha256 = new StandardPasswordEncoder();
        System.out.println(sha256.encode(PASSWORD));
        System.out.println(sha256.encode(PASSWORD));
    }


    // BCrypt Example - Recommended - Uses Random Salt
    @Test
    void testBCrypt() {
        PasswordEncoder bCrypt = new BCryptPasswordEncoder(10);     // The higher the strength the longer it takes
        System.out.println(bCrypt.encode(PASSWORD));
        System.out.println(bCrypt.encode(PASSWORD));
    }
}
