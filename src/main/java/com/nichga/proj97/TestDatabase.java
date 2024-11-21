package com.nichga.proj97;

import com.nichga.proj97.Database.BorrowRepository;
import com.nichga.proj97.Database.MemberRepository;
import com.nichga.proj97.Services.Auth;

public class TestDatabase {
    public static void main(String[] args) {
        Auth auth = new Auth();

        BorrowRepository br = new BorrowRepository();
        if (br.createBorrowRequest(String.valueOf(1), "CSkqvgAACAAJ")) {
            System.out.println("Borrowed request successfully");
        } else {
            System.out.println("Borrowed request failed");
        }
    }
}
