package com.nichga.proj97;

import java.util.List;
import java.util.Objects;

/**
 * Represents a magazine in the library system, extending the abstract class {@link Documents}.
 * <p>
 * A magazine has a issue number and a publication date in addition to the common attributes of a document.
 * This class provides methods to set and retrieve the issue number and publication date of the magazine.
 * </p>
 */
public class Magazine extends Documents {

    private String issueNumber;

    private String publicationDate;

    private int volume;

    public void setIssueNumber(String issueNumber) {
        this.issueNumber = issueNumber;
    }

    public String getIssueNumber() {
        return issueNumber;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getVolume() {
        return volume;
    }

    public Magazine() {}

    /**
     * Constructs a Magazine instance using the provided information.
     *
     * @param docInfoList a list of strings containing the magazine's title, author, issue number, publication date, and volume.
     *                    The expected order is: title, author, issue number, publication date, volume.
     *                    Must contain at least five elements.
     * @throws IllegalArgumentException if the provided list contains insufficient information.
     */
    public Magazine(List<String> docInfoList) {
        if (docInfoList.size() >= 5) {
            this.setType("Magazine");
            this.setTitle(docInfoList.get(0));
            this.setAuthor(docInfoList.get(1));
            this.issueNumber = docInfoList.get(2);
            this.publicationDate = docInfoList.get(3);
            this.volume = Integer.parseInt(docInfoList.get(4));
            if (docInfoList.size() > 5) {
                this.setNumberOfCopies(Integer.parseInt(docInfoList.get(5)));
                this.setCurrentCopies(Integer.parseInt(docInfoList.get(6)));
                this.setTimesBorrowed(Integer.parseInt(docInfoList.get(7)));
            }
        } else {
            throw new IllegalArgumentException("Insufficient information provided for Magazine.");
        }
    }

    /**
     * Returns a string representation of the magazine attributes,
     * including title, author, type, issue number, publication date, and volume.
     *
     * @return a string containing all attributes of the magazine, separated by commas
     */
    public String getInformation() {
        return String.join(",",
                getTitle(),
                getAuthor(),
                issueNumber,
                publicationDate,
                String.valueOf(volume),
                String.valueOf(getNumberOfCopies()),
                String.valueOf(getCurrentCopies()),
                String.valueOf(getTimesBorrowed())
        );
    }

    /**
     * Displays information about the magazine.
     * This method overrides the {@link Documents#displayInfo()} method to include additional
     * specific details.
     */
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Issue Number: " + issueNumber);
        System.out.println("Publication Date: " + publicationDate);
        System.out.println("Volume: " + volume);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Magazine magazine = (Magazine) obj;
        return this.getTitle().equals(magazine.getTitle()) &&
                this.getAuthor().equals(magazine.getAuthor()) &&
                this.issueNumber.equals(magazine.issueNumber) &&
                this.publicationDate.equals(magazine.publicationDate) &&
                this.volume == magazine.volume;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getAuthor(), issueNumber, publicationDate, volume);
    }

}
