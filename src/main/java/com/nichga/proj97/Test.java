package com.nichga.proj97;

import com.nichga.proj97.Model.Books;
import com.nichga.proj97.Model.BooksResponse;
import com.nichga.proj97.Util.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Test {
    public static void main(String[] args) {
        try {
            // Define the query and encode it
            String query = "java programming";
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

                // Parse the JSON response
                String json = response.toString();
                BooksResponse responseObj = JsonParser.ParseJson(json);

                // Print the book details
                if (responseObj != null && !responseObj.getItems().isEmpty()) {
                    for (Books book : responseObj.getItems()) {
                        System.out.println("Book Details: ");
                        System.out.println(book);
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
