package com.nichga.proj97.Model;

public final class BorrowHistory {
    private final int member_id;

    private final String fullname;

    private final String book_id;

    private final String title;

    private final String author;

    private final String overdue;

    private final String status;

    private final String fine;

    public BorrowHistory(int member_id, String fullname, String book_id, String title, String author, String overdue, String status, String fine) {
        this.member_id = member_id;
        this.fullname = fullname;
        this.book_id = book_id;
        this.title = title;
        this.author = author;
        this.overdue = overdue;
        this.status = status;
        this.fine = fine;
    }

    public int getMember_id() {
        return member_id;
    }

    public String getFullname() {
        return fullname;
    }

    public String getBook_id() {
        return book_id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getOverdue() {
        return overdue;
    }

    public String getStatus() {
        return status;
    }

    public String getFine() {
        return fine;
    }
}
