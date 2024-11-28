package com.nichga.proj97;

import com.nichga.proj97.Database.DatabaseConnector;
import com.nichga.proj97.Model.DisplayBook;
import com.nichga.proj97.Services.Auth;
import com.nichga.proj97.Services.DatabaseService;
import com.nichga.proj97.Services.TokenProvider;
import javafx.collections.ObservableList;

import java.sql.Connection;

public class TestDatabase {
    public static void main(String[] args) {
        Connection faker = DatabaseConnector.getInstance().getConnection();
        if (faker != null) {
            System.out.println("Database connection established");
        } else {
            System.out.println("Database connection not established");
        }
    }
}
