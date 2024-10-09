package com.nichga.proj97;

import java.io.BufferedReader;
import java.io.FileReader;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class FileStorage {
    private final String fileName = "docList.txt";

    public void writeDocumentInfo(Documents document) {
        String docInfo = null;

        switch (document.getType()) {
            case "Book":
                docInfo = "[Book]" + ((Book) document).getInformation();
                break;
            case "Thesis":
                docInfo = "[Thesis]" + ((Thesis) document).getInformation();
                break;
            case "Magazine":
                docInfo = "[Magazine]" + ((Magazine) document).getInformation();
                break;
            default:
                System.out.println("Nothing to do.");
                break;
        }

        docInfo += System.lineSeparator();

        try (FileWriter fileWriter = new FileWriter(fileName, true)) {
            if (!docInfo.equals(System.lineSeparator())) {
                fileWriter.write(docInfo);
            } else {
                System.out.println("Nothing to do.");
            }
        } catch (IOException e) {
            System.err.println("An error occurred while writing the document information to the file: " + e.getMessage());
        }
    }

    public CopyOnWriteArrayList<Documents> getDocumentList() {
        CopyOnWriteArrayList<Documents> documentList = new CopyOnWriteArrayList<>();

        try (BufferedReader fileReader = new BufferedReader(new FileReader(fileName))) {
            String lineInfo;
            int index = -1;
            List<String> docInfoList;
            while ((lineInfo = fileReader.readLine()) != null) {
                if (lineInfo.startsWith("[Book]")) {
                    index = 6;
                } else if (lineInfo.startsWith("[Thesis]")) {
                    index = 8;
                } else if (lineInfo.startsWith("[Magazine]")) {
                    index = 10;
                }
                docInfoList = List.of(lineInfo.substring(index).split(","));
                switch (index) {
                    case 6:
                        documentList.add(new Book(docInfoList));
                        break;
                    case 8:
                        documentList.add(new Thesis(docInfoList));
                        break;
                    case 10:
                        documentList.add(new Magazine(docInfoList));
                        break;
                }
            }
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        }

        clearFile();

        return documentList;
    }

    private void clearFile() {
        try (FileWriter fileWriter = new FileWriter(this.fileName, false)) {
            fileWriter.write("");
        } catch (IOException e) {
            System.err.println("An error occurred while clearing the file: " + e.getMessage());
        }
    }
}
