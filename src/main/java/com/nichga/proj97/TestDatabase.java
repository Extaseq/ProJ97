package com.nichga.proj97;

import com.nichga.proj97.Services.Auth;

public class TestDatabase {
    public static void main(String[] args) {
        Auth auth = new Auth();
        if (auth.register("test11211232", "testtest", "Kim Jongo Un")) {
            System.out.println("User registered successfully");
        } else {
            System.out.println("User registration failed");
        }
    }
}
