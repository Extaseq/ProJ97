//package com.nichga.proj97;
//
//import java.util.List;
//import java.util.NoSuchElementException;
//import java.util.concurrent.CopyOnWriteArrayList;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
//public class Library {
//    private CopyOnWriteArrayList<Documents> documentList = new CopyOnWriteArrayList<>();
//
//    /**
//     * Adds a document to the library. If the document already exists in the list,
//     * it updates the number of copies. Otherwise, it adds the new document to the list
//     * and sets the initial number of copies.
//     *
//     * @param document the document to be added to the library
//     * @param copies the number of copies of the document to be added
//     * If the document already exists in the list, the current number of copies and total
//     * number of copies are updated by adding the provided number of copies.
//     * If the document does not exist, it is added to the list with the provided number of copies.
//     */
//    public void addDocument(Documents document, int copies) {
//
//        int idx = documentList.indexOf(document);
//
//        if (idx != -1) {
//            Documents ref = documentList.get(idx);
//            ref.setNumberOfCopies(ref.getNumberOfCopies() + copies);
//            ref.setCurrentCopies(ref.getCurrentCopies() + copies);
//        } else {
//            documentList.add(document);
//            document.setCurrentCopies(copies);
//            document.setNumberOfCopies(copies);
//        }
//    }
//
//    /**
//     * Deletes a document from the library's document list.
//     * If the document does not exist in the list, an exception is thrown.
//     *
//     * @param document the document to be removed from the library
//     * @throws NoSuchElementException if the document is not found in the document list
//     */
//    public void deleteDocument(Documents document) {
//        int index = documentList.indexOf(document);
//
//        if (index == -1) {
//            throw new NoSuchElementException("There is not this document in the library");
//        }
//
//        Documents foundDocument = documentList.get(index);
//
//        foundDocument.displayInfo();
//
//        documentList.remove(index);
//    }
//
//    /**
//     * Searches for documents in the library's document list based on a specific attribute and keyword.
//     * The method filters the document list by applying the provided attribute function and checks if
//     * the attribute value contains the keyword (case-insensitive).
//     * {Assume that the library not too large}
//     *
//     * @param getAttribute a function that extracts a specific attribute (e.g., title, author) from a document
//     * @param key_word the keyword to search for within the specified attribute
//     * @return a list of documents that contain the keyword in the specified attribute
//     */
//    private List<Documents> searchDocument(Function<Documents, String> getAttribute, String key_word) {
//        return documentList.stream()
//                .filter(doc -> getAttribute.apply(doc).toLowerCase().contains(key_word.toLowerCase()))
//                .collect(Collectors.toList());
//    }
//
//    /**
//     * Searches for documents matching the given title, author, and type.
//     * If a parameter is null or empty, that attribute is not considered in the search.
//     *
//     * @param title the title keyword to search for
//     * @param author the author keyword to search for
//     * @param type the type keyword to search for
//     * @return a list of documents that match the search criteria
//     */
//    public List<Documents> searchDocuments(String title, String author, String type) {
//        return documentList.stream()
//                .filter(doc -> (title == null || title.isEmpty() || doc.getTitle().toLowerCase().contains(title.toLowerCase())) &&
//                        (author == null || author.isEmpty() || doc.getAuthor().toLowerCase().contains(author.toLowerCase())) &&
//                        (type == null || type.isEmpty() || doc.getType().toLowerCase().contains(type.toLowerCase())))
//                .collect(Collectors.toList());
//    }
//
//    /**
//     * Searches for documents by title that contain the specified keyword (case insensitive).
//     *
//     * @param key_word the keyword to search for within document titles
//     * @return a list of documents whose titles contain the keyword
//     */
//    public List<Documents> searchDocumentByName(String key_word) {
//        return searchDocument(Documents::getTitle, key_word);
//    }
//
//    /**
//     * Searches for documents by author that contain the specified keyword (case insensitive).
//     *
//     * @param key_word the keyword to search for within document authors
//     * @return a list of documents whose authors contain the keyword
//     */
//    public List<Documents> searchDocumentByAuthor(String key_word) {
//        return searchDocument(Documents::getAuthor, key_word);
//    }
//
//    /**
//     * Searches for documents by type that contain the specified keyword (case insensitive).
//     *
//     * @param key_word the keyword to search for within document types
//     * @return a list of documents whose types contain the keyword
//     */
//    public List<Documents> searchDocumentByType(String key_word) {
//        return searchDocument(Documents::getType, key_word);
//    }
//
//    /**
//     * Allows a user to borrow a document if copies are available.
//     * The method reduces the number of available copies of the document by one
//     * and adds the document to the user's borrowed documents list.
//     *
//     * @param user the user borrowing the document
//     * @param document the document to be borrowed
//     * @throws NoSuchElementException if there are no available copies of the document
//     */
//    public void borrowDocument(User user, Documents document) {
//        int idx = documentList.indexOf(document);
//        Documents ref = documentList.get(idx);
//        if (ref.getCurrentCopies() <= 0) {
//            throw new NoSuchElementException("No available copies of this document");
//        }
//        user.addUserDocuments(ref);
//        ref.setCurrentCopies(ref.getCurrentCopies() - 1);
//    }
//
//    public void setDocumentList(List<Documents> documentList) {
//        this.documentList.clear();
//        this.documentList.addAll(documentList);
//    }
//
//    public void close() {
//        System.out.println("Starting close the library.");
//
//        FileStorage fileStorage = new FileStorage();
//        for (Documents document : documentList) {
//            fileStorage.writeDocumentInfo(document);
//            System.out.println("Added " + document.getTitle() + " to the library.");
//        }
//        documentList.clear();
//    }
//}
