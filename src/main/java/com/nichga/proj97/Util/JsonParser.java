package com.nichga.proj97.Util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nichga.proj97.Model.Book;
import com.nichga.proj97.Model.BookResponses;

import java.time.LocalDate;

public class JsonParser {
    public static Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();

    public static BookResponses ParseJson(String json) {
        return gson.fromJson(json, BookResponses.class);
    }
    public static Book ParseSingleBook(String json) {
        return gson.fromJson(json, Book.class);
    }
}
