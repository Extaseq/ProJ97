package com.nichga.proj97;

import com.nichga.proj97.Model.DisplayBook;
import com.nichga.proj97.Services.Auth;
import com.nichga.proj97.Services.DatabaseService;
import com.nichga.proj97.Services.TokenProvider;
import javafx.collections.ObservableList;

public class TestDatabase {
    public static void main(String[] args) {
        DatabaseService dbs = new DatabaseService();
        ObservableList<DisplayBook> db = dbs.getBookRepo().getAllBook();
        System.out.println(db.size());
    }
}
