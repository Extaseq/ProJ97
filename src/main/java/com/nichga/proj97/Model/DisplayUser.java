package com.nichga.proj97.Model;

public class DisplayUser {
    private final String account_id;
    private final String member_id;
    private final String username;
    private final String fullname;
    private final String phone;
    private final String email;
    private final String joined_date;
    private final String status;

    public DisplayUser(String account_id, String member_id, String username, String fullname,
                       String phone, String email, String joined_date, String status) {
        this.account_id = account_id;
        this.member_id = member_id;
        this.username = username;
        this.fullname = fullname;
        this.phone = phone;
        this.email = email;
        this.joined_date = joined_date;
        this.status = status;
    }

    public String getAccount_id() {
        return account_id;
    }

    public String getMember_id() {
        return member_id;
    }

    public String getUsername() {
        return username;
    }

    public String getFullname() {
        return fullname;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getJoined_date() {
        return joined_date;
    }

    public String getStatus() {
        return status;
    }
}

