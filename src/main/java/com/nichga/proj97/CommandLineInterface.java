package com.nichga.proj97;

import org.w3c.dom.Document;

import java.util.*;

/**
 * The CommandLineInterface class provides a simple menu-driven application for interacting with users via the command line.
 * Users can add, remove, update, and find documents, as well as add users, borrow and return documents, and display user information.
 */
public class CommandLineInterface {

    private static final Library library;

    private static final FileStorage fileStorage;

    private static List<User> userList;

    private static final Scanner scanner;

    private static int autoIncreaseUID;

    static {
        library = new Library();
        userList = new ArrayList<>();
        fileStorage = new FileStorage();
        scanner = new Scanner(System.in);
        autoIncreaseUID = 1;
    }

    public static void main(String[] args) {
        CommandLineInterface cli = new CommandLineInterface();
        cli.start();
    }

    List<Documents> findDocumentByBasicInfo() {
        System.out.println("Enter the document title to search for: ");
        String title = scanner.nextLine();

        System.out.println("Enter the document author: ");
        String author = scanner.nextLine();

        System.out.println("Enter the document type (e.g., Book, Magazine, Thesis): ");
        String type = scanner.nextLine();

        return library.searchDocuments(title, author, type);
    }

    /**
     * Starts the command-line interface and displays the menu.
     * The method listens for user input and processes different options based on the input.
     */
    public void start() {
        int action = -1;

        library.setDocumentList(fileStorage.getDocumentList());

        while (action != 0) {
            displayMenu();
            try {
                System.out.print("Please enter your choice: ");
                action = Integer.parseInt(scanner.nextLine());

                switch (action) {
                    case 0:
                        System.out.println("Exiting the application. Goodbye!");
                        break;
                    case 1:
                        addDocument();
                        break;
                    case 2:
                        removeDocument();
                        break;
                    case 3:
                        updateDocument();
                        break;
                    case 4:
                        findDocument();
                        break;
                    case 5:
                        displayDocument();
                        break;
                    case 6:
                        addUser();
                        break;
                    case 7:
                        borrowDocument();
                        break;
                    case 8:
                        returnDocument();
                        break;
                    case 9:
                        displayUserInfo();
                        break;
                    default:
                        System.out.println("Action is not supported.");
                        System.out.println("Null string 1");
                        break;
                }
            } catch (NumberFormatException e) {
                // Handle invalid input (not a number)
                System.out.println("Action is not supported.");
                System.out.println("Null string 2");
            }
        }

        library.close();

        scanner.close();
    }

    /**
     * Displays the interactive menu with available actions for the user.
     */
    private void displayMenu() {
        System.out.println("\nWelcome to Library Management!");
        System.out.println("[0] Exit");
        System.out.println("[1] Add Document");
        System.out.println("[2] Remove Document");
        System.out.println("[3] Update Document");
        System.out.println("[4] Find Document");
        System.out.println("[5] Display Document");
        System.out.println("[6] Add User");
        System.out.println("[7] Borrow Document");
        System.out.println("[8] Return Document");
        System.out.println("[9] Display User Info");
    }

    private void addDocument() {
        List<String> docInfo = new ArrayList<>();

        System.out.println("Enter document title: ");
        String title = scanner.nextLine();

        System.out.println("Enter document author: ");
        String author = scanner.nextLine();

        System.out.println("Enter document type (e.g., Book, Magazine, Thesis): ");
        String type = scanner.nextLine();

        System.out.println("Enter number of copies: ");
        int numberOfCopies;
        while (true) {
            try {
                numberOfCopies = Integer.parseInt(scanner.nextLine());
                if (numberOfCopies <= 0) {
                    System.out.println("Number of copies must be greater than 0. Try again: ");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please enter a valid number: ");
            }
        }

        String numberOfCopiesStr = String.valueOf(numberOfCopies);

        Documents newDocument;
        switch (type.toLowerCase()) {
            case "book":
                System.out.println("Enter document publisher: ");
                String publisher = scanner.nextLine();

                System.out.println("Enter document publication year: ");
                String publicationYear = scanner.nextLine();

                docInfo.addAll(Arrays.asList(
                    title,
                    author,
                    publisher,
                    publicationYear,
                    numberOfCopiesStr,
                    "0",
                    "0"
                ));

                System.out.println("DocInfo size: " + docInfo.size());

                newDocument = new Book(docInfo);
                break;

            case "thesis":
                System.out.println("Enter document university: ");
                String university = scanner.nextLine();

                System.out.println("Enter document degree: ");
                String degree = scanner.nextLine();

                System.out.println("Enter document supervisor: ");
                String supervisor = scanner.nextLine();

                System.out.println("Enter document yearSubmitted: ");
                String yearSubmitted = scanner.nextLine();

                docInfo.addAll(Arrays.asList(
                    title,
                    author,
                    university,
                    degree,
                    supervisor,
                    yearSubmitted
                ));

                System.out.println("DocInfo size: " + docInfo.size());

                newDocument = new Thesis(docInfo);
                break;

            case "magazine":
                System.out.println("Enter document issueNumber: ");
                String issueNumber = scanner.nextLine();

                System.out.println("Enter document publicationDate: ");
                String publicationDate = scanner.nextLine();

                System.out.println("Enter document volume: ");
                String volume = scanner.nextLine();

                docInfo.addAll(Arrays.asList(
                        title,
                        author,
                        issueNumber,
                        publicationDate,
                        volume
                ));

                System.out.println("DocInfo size: " + docInfo.size());

                newDocument = new Magazine(docInfo);
                break;

            default:
                System.out.println("Invalid document type. Document not added.");
                return;
        }

        // Add the new document to the list
        library.addDocument(newDocument, numberOfCopies);
        System.out.println("Document added successfully.");
    }

    private void removeDocument() {
        List<Documents> result = findDocumentByBasicInfo();

        if (result.isEmpty()) {
            System.out.println("No documents found.");
            return;
        }

        int currentIndex = 0;

        while (true) {
            Documents currentDocument = result.get(currentIndex);
            System.out.println("Current Document:");
            currentDocument.displayInfo();
            System.out.println("\nUse L/R to navigate, Y to delete, E to exit.");

            // User input for navigation
            String input = scanner.nextLine();

            switch (input.toLowerCase()) {
                case "l":
                    if (currentIndex > 0) {
                        currentIndex--;
                    } else {
                        System.out.println("You are at the first document.");
                    }
                    break;
                case "r":
                    if (currentIndex < result.size() - 1) {
                        currentIndex++;
                    } else {
                        System.out.println("You are at the last document.");
                    }
                    break;
                case "y":
                    try {
                        library.deleteDocument(currentDocument);  // Delete the current document
                        System.out.println("Document deleted successfully!");
                        result.remove(currentIndex);  // Remove from the list
                        if (result.isEmpty()) {
                            System.out.println("No documents left to display.");
                            return;  // Exit if no documents are left
                        }
                        // Adjust index if the last document was deleted
                        if (currentIndex >= result.size()) {
                            currentIndex = result.size() - 1;
                        }
                    } catch (NoSuchElementException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case "e":
                    System.out.println("Exiting document removal.");
                    return;  // Exit the loop and method
                default:
                    System.out.println("Invalid input! Please enter 'L', 'R', 'Y', or 'E'.");
            }
        }
    }

    /**
     * Placeholder method for updating a document. Implementation can be added later.
     */
    private void updateDocument() {
        List<Documents> result = findDocumentByBasicInfo();

        if (result.isEmpty()) {
            System.out.println("No documents found.");
            return;
        }

        int currentIndex = 0;
        while (true) {
            Documents document = result.get(currentIndex);
            document.displayInfo();

            System.out.println("\nChoose an action:");
            System.out.println("1. Edit document");
            System.out.println("2. Next document");
            System.out.println("3. Previous document");
            System.out.println("4. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    document.editInformation();
                    System.out.println("Document updated successfully!");
                }
                case 2 -> currentIndex = (currentIndex + 1) % result.size();
                case 3 -> currentIndex = (currentIndex - 1 + result.size()) % result.size();
                case 4 -> {
                    System.out.println("Exiting document update.");
                    return;
                }
                default -> System.out.println("Invalid choice. Please select 1-4.");
            }
        }
    }

    /**
     * Placeholder method for finding a document. Implementation can be added later.
     */
    private void findDocument() {
        List<Documents> result = findDocumentByBasicInfo();

        if (result.isEmpty()) {
            System.out.println("No documents found.");
            return;
        }

        int currentIndex = 0;
        while (true) {
            Documents document = result.get(currentIndex);
            document.displayInfo();

            System.out.println("\nChoose an action:");
            System.out.println("1. Next document");
            System.out.println("2. Previous document");
            System.out.println("3. Exit");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> currentIndex = (currentIndex + 1) % result.size();
                case 2 -> currentIndex = (currentIndex - 1 + result.size()) % result.size();
                case 3 -> {
                    System.out.println("Exiting document finder.");
                    return;
                }
                default -> System.out.println("Invalid choice. Please select 1-3.");
            }
        }
    }

    /**
     * Placeholder method for displaying document information. Implementation can be added later.
     */
    private void displayDocument() {
        // Duplicate functionality
        findDocument();
    }

    /**
     * Placeholder method for adding a user. Implementation can be added later.
     */
    private void addUser() {
        System.out.println("Enter username: ");
        String username = scanner.nextLine();

        System.out.println("Enter password: ");
        String password = scanner.nextLine();

        String rePassword;

        while (true) {
            System.out.println("Re-enter password: ");
            rePassword = scanner.nextLine();

            if (!password.equals(rePassword)) {
                System.out.println("Passwords do not match. Please try again.");
                continue;
            } else {
                break;
            }
        }

        userList.add(new User(autoIncreaseUID++, username, password));

        System.out.println("Account created successfully.");
    }

    /**
     * Placeholder method for borrowing a document. Implementation can be added later.
     */
    private void borrowDocument() {
        List<Documents> result = findDocumentByBasicInfo();

        if (result.isEmpty()) {
            System.out.println("No documents found.");
            return;
        }

        int currentIndex = 0;
        while (true) {
            Documents document = result.get(currentIndex);
            document.displayInfo();

            System.out.println("\nChoose an action:");
            System.out.println("1. Next document");
            System.out.println("2. Previous document");
            System.out.println("3. Borrow document");
            System.out.println("4. Exit");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> currentIndex = (currentIndex + 1) % result.size();
                case 2 -> currentIndex = (currentIndex - 1 + result.size()) % result.size();
                case 3 -> {
                    System.out.println("Enter userID: ");
                    int userID = scanner.nextInt();
                    library.borrowDocument(userList.get(userID - 1), document);
                }
                case 4 -> {
                    System.out.println("Exiting document borrow.");
                    return;
                }
                default -> System.out.println("Invalid choice. Please select 1-3.");
            }
        }
    }

    /**
     * Placeholder method for returning a borrowed document. Implementation can be added later.
     */
    private void returnDocument() {
        System.out.println("Enter userID: ");
        int userID = scanner.nextInt();
        List<Documents> docList = userList.get(userID - 1).getDocumentList();
    }

    /**
     * Placeholder method for displaying user information. Implementation can be added later.
     */
    private void displayUserInfo() {
        System.out.println("Display User Info functionality goes here.");
    }
}
