package com.nichga.proj97.Model;

public class User {
    public int account_id;
    public String username;

    public User(int account_id, String username) {
        this.account_id = account_id;
        this.username = username;
    }

    public int getAccountID() {
        return account_id;
    }

    public String getUsername() {
        return username;
    }

    public int getBorrowedBook() {
        return 0;
    }

    public int getOverdueBook() {
        return 0;
    }

    @Override
    public String toString() {
        return "User: [" + "account_id = " + account_id + ", username = " + username + ']';
    }
}
