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
        Auth auth = new Auth();
        if (auth.register("KinasTomes", "ahihi123!", "Kim Jong Un")) {
            System.out.println("Register successfully");
        } else {
            System.out.println("Register failed");
        }
    }
}
