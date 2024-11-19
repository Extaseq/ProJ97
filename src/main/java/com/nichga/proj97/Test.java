package com.nichga.proj97;

import java.lang.reflect.Member;
import java.sql.Connection;
import java.sql.ResultSet;

import Database.DatabaseConnector;
import Database.*;

public class Test {
    public static void main(String[] args) {
        // Kết nối với database
        Connection connection = DatabaseConnector.getInstance().getConnection();
        if (connection == null) {
            System.out.println("Database connection failed!");
            return;
        }

        // Khởi tạo repository
        UserRepository userRepo = new UserRepository();
        if (userRepo.changePassword("kinastomes", "123")) {
            System.out.println("Password changed!");
        } else {
            System.out.println("Password change failed!");
        }
    }
}
