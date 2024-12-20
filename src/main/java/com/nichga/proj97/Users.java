package com.nichga.proj97;

import com.google.zxing.WriterException;
import com.nichga.proj97.Database.BookRepository;
import com.nichga.proj97.Database.BorrowRepository;
import com.nichga.proj97.Database.TokenRepository;
import com.nichga.proj97.Services.DatabaseService;
import com.nichga.proj97.Services.TokenProvider;
import javafx.scene.control.Alert;

import java.awt.image.BufferedImage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Users {

    private String name;

    private String username;

    private String password;

    private String email;

    private String phone;

    private String address;

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
    public Users() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BufferedImage borrow(String bookId) {
        DatabaseService ds = new DatabaseService();
        TokenProvider tp = new TokenProvider();
        BookRepository br = ds.getBookRepo();
        BorrowRepository bre = ds.getBorrowRepo();
        TokenRepository tr = ds.getTokenRepo();
        if (tr.existToken(String.valueOf(id), bookId)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You have already created a borrow request of this book!");
            alert.showAndWait();
            return null;
        }
        String token = bre.createBorrowRequest(String.valueOf(id),bookId);
        if (token == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You can't borrow this book");
            alert.showAndWait();
            return null;
        }
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
    public String[] recommendDoc(int id) {
        String[] result = new String[5];
        DatabaseService ds = new DatabaseService();
        try (ResultSet rs = ds.getBookRepo().recommendDocument(Integer.toString(id))) {
            int i = 0;
            while (rs != null && rs.next() && i < 5) {
                result[i++] = rs.getString("book_id");
            }
        } catch (SQLException e) {
            System.out.println("Error processing recommendations: " + e.getMessage());
        }
        return result;
    }
}
