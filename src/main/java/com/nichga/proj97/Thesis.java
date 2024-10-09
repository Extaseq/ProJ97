package com.nichga.proj97;

import java.util.List;
import java.util.Objects;

/**
 * Represents a thesis in the library system, extending the abstract class {@link Documents}.
 * <p>
 * A thesis has university, degree, supervisor, summited year
 * in addition to the common attributes of a document.
 * This class provides methods to set and retrieve all attributes of the the thesis.
 * </p>
 */
public class Thesis extends Documents {

    private String university;

    private String degree;

    private String supervisor;

    private int yearSubmitted;

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getDegree() {
        return degree;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getUniversity() {
        return university;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setYearSubmitted(int yearSubmitted) {
        this.yearSubmitted = yearSubmitted;
    }

    public int getYearSubmitted() {
        return yearSubmitted;
    }

    public Thesis() {}

    /**
     * Constructs a Thesis instance using the provided information.
     *
     * @param docInfoList a list of strings containing the thesis's title, author, university, degree, supervisor, and year submitted.
     *                    The expected order is: title, author, university, degree, supervisor, year submitted.
     *                    Must contain at least six elements.
     * @throws IllegalArgumentException if the provided list contains insufficient information.
     */
    public Thesis(List<String> docInfoList) {
        if (docInfoList.size() >= 6) {
            this.setTitle(docInfoList.get(0));
            this.setAuthor(docInfoList.get(1));
            this.setType("Thesis");
            this.university = docInfoList.get(2);
            this.degree = docInfoList.get(3);
            this.supervisor = docInfoList.get(4);
            this.yearSubmitted = Integer.parseInt(docInfoList.get(5));
            if (docInfoList.size() > 8) {
                this.setNumberOfCopies(Integer.parseInt(docInfoList.get(6)));
                this.setCurrentCopies(Integer.parseInt(docInfoList.get(7)));
                this.setTimesBorrowed(Integer.parseInt(docInfoList.get(8)));
            }
        } else {
            throw new IllegalArgumentException("Insufficient information provided for Thesis.");
        }
    }

    /**
     * Returns a string representation of the thesis attributes,
     * including title, author, type, university, degree, supervisor, and year submitted.
     *
     * @return a string containing all attributes of the thesis, separated by commas
     */
    public String getInformation() {
        return String.join(",",
                getTitle(),
                getAuthor(),
                university,
                degree,
                supervisor,
                String.valueOf(yearSubmitted),
                String.valueOf(getNumberOfCopies()),
                String.valueOf(getCurrentCopies()),
                String.valueOf(getTimesBorrowed())
        );
    }

    /**
     * Displays information about the thesis.
     * This method overrides the {@link Documents#displayInfo()} method to include additional
     * specific details.
     */
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("University: " + university);
        System.out.println("Degree: " + degree);
        System.out.println("Supervisor: " + supervisor);
        System.out.println("Year Submitted: " + yearSubmitted);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Thesis thesis = (Thesis) obj;
        return this.getTitle().equals(thesis.getTitle()) &&
                this.getAuthor().equals(thesis.getAuthor()) &&
                this.university.equals(thesis.university) &&
                this.degree.equals(thesis.degree) &&
                this.supervisor.equals(thesis.supervisor) &&
                this.yearSubmitted == thesis.yearSubmitted;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getAuthor(), university, degree, supervisor, yearSubmitted);
    }
}
