package com.nichga.proj97;

import com.nichga.proj97.Database.BookRepository;
import com.nichga.proj97.Model.Books;
import com.nichga.proj97.Model.BooksResponse;
import com.nichga.proj97.Util.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        try {
            // Define the query and encode it
            String query = "fuck";
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

                // Process the book details
                if (responseObj != null && !responseObj.getItems().isEmpty()) {
                    BookRepository br = new BookRepository();
                    for (Books book : responseObj.getItems()) {
                        System.out.println("Book Details: ");
                        System.out.println(book);

                        // Extract data safely
                        String authors = book.getVolumeInfo().getAuthors() != null
                                ? String.join(", ", book.getVolumeInfo().getAuthors())
                                : "Unknown Author";

                        String bookId = book.getId();
                        String title = book.getVolumeInfo().getTitle();
                        String isbn = book.getVolumeInfo().getIndustryIdentifiers() != null
                                && !book.getVolumeInfo().getIndustryIdentifiers().isEmpty()
                                ? book.getVolumeInfo().getIndustryIdentifiers().get(0).getIdentifier()
                                : "Unknown ISBN";

                        // Get published year using LocalDate and DateTimeFormatter
                        String publishedDate = book.getVolumeInfo().getPublishedDate();
                        String year = "1900";
                        if (publishedDate != "Unknown Year") {
                            try {
                                // Handle different possible formats for the publication date
                                if (publishedDate.length() == 4) {
                                    // Only year (e.g. "2007")
                                    year = publishedDate;
                                } else if (publishedDate.length() > 4) {
                                    year = String.valueOf(publishedDate.substring(0,4));  // Convert LocalDate to String
                                }
                            } catch (Exception e) {
                                System.out.println("Invalid published date format: " + publishedDate);
                            }
                        }

                        String publisher = book.getVolumeInfo().getPublisher() != null
                                ? book.getVolumeInfo().getPublisher()
                                : "Unknown Publisher";
                        int result = br.addNewBook(year, bookId, title, authors, publisher, isbn);

                        if (result > 0) {
                            System.out.println("Book added successfully to the database.");
                        } else {
                            System.out.println("Failed to add book: " + title);
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
