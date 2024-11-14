package com.nichga.proj97;

import java.util.*;

public class User {
    private int id;

    private String username;

    private String password;

    private String name;

    private int permission;

    private List<Documents> userDocuments;

    private List<Documents> overdueDocuments;

    private Queue<Documents> last5Documents;

    private Map<String, Integer> documentTags;

    private List<Documents> recommendDoc;

    List<String> mostTags;

    private final int maxRecommend = 5;

    {
        userDocuments = new ArrayList<>();
        documentTags = new HashMap<>();
        overdueDocuments = new ArrayList<>();
        mostTags = new ArrayList<>();
        last5Documents = new LinkedList<>();
    }

    User(int id, String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getUserID() {
        return this.id;
    }

    public void setID(int id) {
        this.id = id;
    }

    String getUserName() {
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

    int getBorrowedBook() {
        return userDocuments.size();
    }

    int getOverdueBook() {
        return overdueDocuments.size();
    }

    List<Documents> getDocumentList() {
        return userDocuments;
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
}
