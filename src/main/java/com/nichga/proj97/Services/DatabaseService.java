package com.nichga.proj97.Services;

import com.nichga.proj97.Database.*;

public class DatabaseService {
    private final BookRepository bookRepository;
    private final BorrowRepository borrowRepository;
    private final MemberRepository memberRepository;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final ReadingHistoryRepository readingHistoryRepository;

    public DatabaseService() {
        bookRepository = new BookRepository();
        borrowRepository = new BorrowRepository();
        memberRepository = new MemberRepository();
        tokenRepository = new TokenRepository();
        userRepository = new UserRepository();
        readingHistoryRepository = new ReadingHistoryRepository();
    }

    public BookRepository getBookRepo() {
        return bookRepository;
    }

    public BorrowRepository getBorrowRepo() {
        return borrowRepository;
    }

    public MemberRepository getMemberRepo() {
        return memberRepository;
    }

    public TokenRepository getTokenRepo() {
        return tokenRepository;
    }

    public UserRepository getUserRepo() {
        return userRepository;
    }

    public ReadingHistoryRepository getReadingHistoryRepository() { return readingHistoryRepository; }
}
