CREATE DATABASE Library;

USE Library;

CREATE TABLE `Authors` (
  `author_id` integer PRIMARY KEY,
  `name` varchar(50),
  `biography` text
);

CREATE TABLE `Books` (
  `book_id` integer PRIMARY KEY AUTO_INCREMENT,
  `title` varchar(100),
  `author_id` integer,
  `publisher_id` integer,
  `genre` varchar(100),
  `published_year` year,
  `isbn` varchar(13),
  `copies_available` integer,
  `views` integer DEFAULT 0
);

CREATE TABLE `Publishers` (
  `publisher_id` integer PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255),
  `address` varchar(255)
);

CREATE TABLE `Members` (
  `member_id` integer PRIMARY KEY AUTO_INCREMENT,
  `fullname` varchar(255),
  `address` varchar(255),
  `email` varchar(100),
  `phone` varchar(20),
  `join_date` date
);

CREATE TABLE `UserAccounts` (
  `account_id` integer PRIMARY KEY AUTO_INCREMENT,
  `member_id` integer UNIQUE,
  `username` varchar(100) UNIQUE,
  `password_hash` varchar(255),
  `salt` varchar(255),
  `created_time` timestamp,
  `updated_time` timestamp
);

CREATE TABLE `Borrow` (
  `borrow_id` integer PRIMARY KEY AUTO_INCREMENT,
  `member_id` integer,
  `book_id` integer,
  `borrow_date` date,
  `due_date` date,
  `return_date` date,
  `rating` integer,
  `review_text` text
);

CREATE TABLE `Fines` (
  `fine_id` integer PRIMARY KEY AUTO_INCREMENT,
  `borrow_id` integer,
  `amount` decimal(10,2),
  `paid` bool
);

CREATE TABLE `ReadingHistory` (
  `history_id` integer PRIMARY KEY AUTO_INCREMENT,
  `member_id` integer,
  `book_id` integer,
  `reading_date` date,
  `status` enum('Borrowed', 'Completed', 'In Progress')
);

ALTER TABLE `Books` ADD FOREIGN KEY (`author_id`) REFERENCES `Authors` (`author_id`);

ALTER TABLE `Books` ADD FOREIGN KEY (`publisher_id`) REFERENCES `Publishers` (`publisher_id`);

ALTER TABLE `Borrow` ADD FOREIGN KEY (`book_id`) REFERENCES `Books` (`book_id`);

ALTER TABLE `Borrow` ADD FOREIGN KEY (`member_id`) REFERENCES `Members` (`member_id`);

ALTER TABLE `Fines` ADD FOREIGN KEY (`borrow_id`) REFERENCES `Borrow` (`borrow_id`);

ALTER TABLE `ReadingHistory` ADD FOREIGN KEY (`book_id`) REFERENCES `Books` (`book_id`);

ALTER TABLE `ReadingHistory` ADD FOREIGN KEY (`member_id`) REFERENCES `Members` (`member_id`);

ALTER TABLE `Members` ADD FOREIGN KEY (`member_id`) REFERENCES `UserAccounts` (`member_id`);

