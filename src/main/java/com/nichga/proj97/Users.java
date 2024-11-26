package com.nichga.proj97;

import com.google.zxing.WriterException;
import com.nichga.proj97.Database.BookRepository;
import com.nichga.proj97.Services.DatabaseService;
import com.nichga.proj97.Services.TokenProvider;

import java.awt.image.BufferedImage;
import java.util.*;

public class Users {

    private String name;

    private String username;

    private String password;

    private int id;

    private List<Documents> userDocuments;

    private Queue<Documents> last5Documents;

    private Map<String, Integer> documentTags;

    private List<Documents> recommendDoc;

    List<String> mostTags;

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    private final int maxRecommend = 5;

    {
        userDocuments = new ArrayList<>();
        documentTags = new HashMap<>();
        mostTags = new ArrayList<>();
        last5Documents = new LinkedList<>();
    }

    /**
     * Default Constructor
     */
    Users() {
        name = "Unknown";
    }

    Users(String username, String password) {
        this.username = username;
        this.password = password;
    }

    Users(String name, List<Documents> doc) {
        this.name = name;
        userDocuments = doc;
    }

    Users(String name) {
        this.name = name;
    }

    Users(List<Documents> doc) {
        name = "Unknown";
        userDocuments = doc;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    List<Documents> getUserDocuments() {
        return userDocuments;
    }

    void setUserDocuments(List<Documents> doc) {
        userDocuments.clear();
        userDocuments.addAll(doc);
    }

    void setRecommendDoc(List<Documents> doc) {
        recommendDoc = doc;
    }
    /**
     * Update List tags after Add or Delete.
     */
    void update() {
        //Sort tags by value
        List<Map.Entry<String, Integer>> tagList = new ArrayList<>(documentTags.entrySet());
        tagList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        //Find most 5 tags
        List<String> Tags = new ArrayList<>();
        for (Map.Entry<String, Integer> e : tagList) {
            if (Tags.size() < maxRecommend) {
                Tags.add(e.getKey());
            } else {
                break;
            }
        }
        mostTags.clear();
        mostTags.addAll(Tags);
    }

    /**
     * Add Document.
     * @param doc new document
     */
    void addUserDocuments(Documents doc) {
        userDocuments.add(doc);

        //Add doc to Queue
        last5Documents.offer(doc);

        //Update tags, count to Map
        for(String s : doc.tag){
            int count = documentTags.getOrDefault(s, 0);
            documentTags.put(s, ++count);
        }

        //Remove last document if size > 5
        if (last5Documents.size() > maxRecommend) {
            Documents outDoc = last5Documents.poll();
            for(String x : outDoc.tag){
                int count = documentTags.getOrDefault(x, 0);
                documentTags.put(x, --count);
                if (count <= 0) {
                    documentTags.remove(x);
                }
            }
        }
        update();
    }

    /**
     * Delete document by Title.
     * @param docTitle title
     */
    void deleteUserDocuments(String docTitle) {
        for (Documents doc : userDocuments) {
            if (doc.getTitle().equals(docTitle)) {
                userDocuments.remove(doc);
                last5Documents.remove(doc);
                for(String x : doc.tag){
                    int count = documentTags.getOrDefault(x, 0);
                    documentTags.put(x, --count);
                    if (count <= 0) {
                        documentTags.remove(x);
                    }
                }
                break;
            }
        }
        update();
    }

    /**
     * Delete document.
     * @param doc document
     */
    void deleteUserDocuments(Documents doc) {
        userDocuments.remove(doc);
        last5Documents.remove(doc);
        for(String x : doc.tag){
            int count = documentTags.getOrDefault(x, 0);
            documentTags.put(x, --count);
            if (count <= 0) {
                documentTags.remove(x);
            }
        }
        update();
    }

    public BufferedImage borrow(String bookId) {
        DatabaseService ds = new DatabaseService();
        TokenProvider tp = new TokenProvider();
        BookRepository br = ds.getBookRepo();
        String token = tp.generateToken();
        try {
            BufferedImage qrCode = tp.generateQRCode(token);
            br.adjustAfterBorrow(bookId);
            return qrCode;

        } catch (WriterException e) {
            System.out.println("Cannot generate QR Code: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An error occurred while processing borrow: " + e.getMessage());
        }
        return null;
    }
}
