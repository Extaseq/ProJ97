package com.nichga.proj97.Util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nichga.proj97.Model.Books;
import com.nichga.proj97.Model.BooksResponse;

import java.time.LocalDate;

public class JsonParser {
    public static Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();

    public static BooksResponse ParseJson(String json) {
        return gson.fromJson(json, BooksResponse.class);
    }
}
