package com.nichga.proj97;

import com.nichga.proj97.Database.BorrowRepository;

public class TestDatabase {
    public static void main(String[] args) {
        BorrowRepository testRepo = new BorrowRepository();
        String token = testRepo.createBorrowRequest("1", "p2xP5hOp4mMC");
        if (token != null) {
            System.out.println(token);
            testRepo.applyBorrowRequest(token);
            System.out.println("Borrow successfully");
        } else {
            System.out.println("No token found");
        }
    }
}
