package com.nichga.proj97;

import com.nichga.proj97.Database.BookRepository;
import com.nichga.proj97.Model.Book;
import com.nichga.proj97.Model.BookResponses;
import com.nichga.proj97.Util.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Test {
    private final static BookRepository bookRepo = new BookRepository();

    public static void main(String[] args) {
        try {
            // Define the query and encode it
            String query = "Marvel";
            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
            String apiKey = "AIzaSyA5B1G2E0gdk-1vag_sJTrsPKOlh7O2y_Y";
            String urlString = "https://www.googleapis.com/books/v1/volumes?q=" + encodedQuery + "&key=" + apiKey + "&maxResults=15";

            // Create a URL object
            URL url = new URL(urlString);

            // Open a connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Check the response code
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) { // HTTP OK
                // Read the response
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine).append("\n");
                }
                in.close();

                String json = response.toString();
                BookResponses responseObj = JsonParser.ParseJson(json);

                // Print the book details
                if (responseObj != null && !responseObj.getItems().isEmpty()) {
                    for (Book book : responseObj.getItems()) {
                        System.out.println("Book Details: ");
//                        System.out.println(book);
                        if (bookRepo.insertBook(book)) {
                            System.out.println("Book added!");
                        } else {
                            System.out.println("Book not added!");
                        }
                    }
                } else {
                    System.out.println("No books found.");
                }
            } else {
                System.out.println("Error: HTTP " + responseCode);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
