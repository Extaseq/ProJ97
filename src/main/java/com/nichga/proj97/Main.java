package com.nichga.proj97;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Library lib = new Library();
        Book alice = new Book();
        alice.setTitle("Alice");
        alice.setAuthor("Alice");
        alice.setPublisher("Le Dat");
        alice.setType("Book");
        lib.addDocument(alice, 100);

        List<Documents> result = lib.searchDocumentByType("Book");

        for (Documents doc : result) {
            doc.displayInfo();
        }
    }
}