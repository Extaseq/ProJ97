package com.nichga.proj97;

import com.nichga.proj97.Services.Auth;
import com.nichga.proj97.Services.DatabaseService;
import com.nichga.proj97.Services.TokenProvider;

public class TestDatabase {
    public static void main(String[] args) {
        DatabaseService dbs = new DatabaseService();
        String newToken = dbs.getBorrowRepo().createBorrowRequest("3", "ipb8RLh9JIAC");
        if (newToken != null) {
            System.out.println("Borrowed token: " + newToken);
        } else {
            System.out.println("Borrowed token is null");
        }
        if (dbs.getBorrowRepo().applyBorrowRequest(newToken)) {
            System.out.println("Borrowed token applied");
        } else {
            System.out.println("Borrowed token does not applied");
        }
    }
}
