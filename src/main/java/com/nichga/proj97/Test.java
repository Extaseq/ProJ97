package com.nichga.proj97;

import java.lang.reflect.Member;
import java.sql.Connection;
import java.sql.ResultSet;

import Database.DatabaseConnector;
import Database.*;
import Services.Auth;

public class Test {
    public static void main(String[] args) {
        // Kết nối với database
        Connection connection = DatabaseConnector.getInstance().getConnection();
        if (connection == null) {
            System.out.println("Database connection failed!");
            return;
        }
    }
}
