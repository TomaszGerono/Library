-- Clear existing data (optional, uncomment if needed)
-- DELETE FROM book_authors;
-- DELETE FROM book_topics;
-- DELETE FROM books;
-- DELETE FROM authors;
-- DELETE FROM genres;
-- DELETE FROM categories;
-- DELETE FROM topics;
-- DELETE FROM sections;
-- DELETE FROM publishers;
-- DELETE FROM series;

-- Initialize sequence tables
INSERT INTO authors_seq (next_val) VALUES (100);
INSERT INTO book_seq (next_val) VALUES (100);
INSERT INTO publishers_seq (next_val) VALUES (100);
INSERT INTO topics_seq (next_val) VALUES (100);

-- Genres
INSERT INTO genres (id, name) VALUES(1, 'Fiction');
INSERT INTO genres (id, name) VALUES(2, 'Non-Fiction');
INSERT INTO genres (id, name) VALUES(3, 'Poetry');
INSERT INTO genres (id, name) VALUES(4, 'Drama');
INSERT INTO genres (id, name) VALUES(5, 'Biography');
INSERT INTO genres (id, name) VALUES(6, 'Self-Help');
INSERT INTO genres (id, name) VALUES(7, 'History');

-- Categories
INSERT INTO categories(id, name, is_custom) VALUES(1, 'Action and Adventure', 'F');
INSERT INTO categories(id, name, is_custom) VALUES(2, 'Comedy', 'F');
INSERT INTO categories(id, name, is_custom) VALUES(3, 'Crime and Mystery', 'F');
INSERT INTO categories(id, name, is_custom) VALUES(4, 'Speculative Fiction', 'F');
INSERT INTO categories(id, name, is_custom) VALUES(5, 'Fantasy', 'F');
INSERT INTO categories(id, name, is_custom) VALUES(6, 'Horror', 'F');
INSERT INTO categories(id, name, is_custom) VALUES(7, 'Science Fiction', 'F');
INSERT INTO categories(id, name, is_custom) VALUES(8, 'Science Fantasy', 'F');
INSERT INTO categories(id, name, is_custom) VALUES(9, 'Superhero', 'F');
INSERT INTO categories(id, name, is_custom) VALUES(10, 'Romance', 'F');
INSERT INTO categories(id, name, is_custom) VALUES(11, 'Thriller', 'F');
INSERT INTO categories(id, name, is_custom) VALUES(12, 'Historical Fiction', 'F');
INSERT INTO categories(id, name, is_custom) VALUES(13, 'Literary Fiction', 'F');
INSERT INTO categories(id, name, is_custom) VALUES(14, 'Philosophy', 'F');
INSERT INTO categories(id, name, is_custom) VALUES(15, 'Technology', 'F');

-- Topics
INSERT INTO topics (id, name) VALUES (1, 'IT');
INSERT INTO topics (id, name) VALUES (2, 'School');
INSERT INTO topics (id, name) VALUES (3, 'Science');
INSERT INTO topics (id, name) VALUES (4, 'Relax');
INSERT INTO topics (id, name) VALUES (5, 'Religion');
INSERT INTO topics (id, name) VALUES (6, 'Philosophy');
INSERT INTO topics (id, name) VALUES (7, 'Politics');
INSERT INTO topics (id, name) VALUES (8, 'Psychology');
INSERT INTO topics (id, name) VALUES (9, 'Business');
INSERT INTO topics (id, name) VALUES (10, 'Art');
INSERT INTO topics (id, name) VALUES (11, 'History');
INSERT INTO topics (id, name) VALUES (12, 'Nature');

-- Sections
INSERT INTO sections (section_id, name) VALUES (1, 'Classic Literature');
INSERT INTO sections (section_id, name) VALUES (2, 'Modern Fiction');
INSERT INTO sections (section_id, name) VALUES (3, 'Technical Books');
INSERT INTO sections (section_id, name) VALUES (4, 'Reference');
INSERT INTO sections (section_id, name) VALUES (5, 'Young Adult');
INSERT INTO sections (section_id, name) VALUES (6, 'Children');
INSERT INTO sections (section_id, name) VALUES (7, 'Polish Literature');

-- Publishers
INSERT INTO publishers(id, name) VALUES(1, 'Wydawnictwo Literackie');
INSERT INTO publishers(id, name) VALUES(2, 'PIW');
INSERT INTO publishers(id, name) VALUES(3, 'Addison-Wesley');
INSERT INTO publishers(id, name) VALUES(4, 'Prentice Hall');
INSERT INTO publishers(id, name) VALUES(5, 'Pearson');
INSERT INTO publishers(id, name) VALUES(6, 'Penguin Books');
INSERT INTO publishers(id, name) VALUES(7, 'Secker and Warburg');
INSERT INTO publishers(id, name) VALUES(8, 'George Allen & Unwin');
INSERT INTO publishers(id, name) VALUES(9, 'Houghton Mifflin');
INSERT INTO publishers(id, name) VALUES(10, 'T. Egerton');
INSERT INTO publishers(id, name) VALUES(11, 'Charles Scribners Sons');
INSERT INTO publishers(id, name) VALUES(12, 'Editorial Sudamericana');
INSERT INTO publishers(id, name) VALUES(13, 'Collins Crime Club');
INSERT INTO publishers(id, name) VALUES(14, 'Gnome Press');
INSERT INTO publishers(id, name) VALUES(15, 'Doubleday');
INSERT INTO publishers(id, name) VALUES(16, 'Ballantine Books');
INSERT INTO publishers(id, name) VALUES(17, 'Viking Press');
INSERT INTO publishers(id, name) VALUES(18, 'Bloomsbury');
INSERT INTO publishers(id, name) VALUES(19, 'William Morrow');
INSERT INTO publishers(id, name) VALUES(20, 'McClelland & Stewart');
INSERT INTO publishers(id, name) VALUES(21, 'Kodansha');
INSERT INTO publishers(id, name) VALUES(22, 'Delacorte Press');
INSERT INTO publishers(id, name) VALUES(23, 'Harvill Secker');
INSERT INTO publishers(id, name) VALUES(24, 'Little, Brown and Company');
INSERT INTO publishers(id, name) VALUES(25, 'Farrar, Straus and Giroux');
INSERT INTO publishers(id, name) VALUES(26, 'Beacon Press');
INSERT INTO publishers(id, name) VALUES(27, 'Random House');
INSERT INTO publishers(id, name) VALUES(28, 'Crown Publishing');
INSERT INTO publishers(id, name) VALUES(29, 'O''Reilly Media');
INSERT INTO publishers(id, name) VALUES(30, 'Manning Publications');

-- Authors (Polish Literature)
INSERT INTO authors(id, first_name, middle_name, last_name, monastery, title)
VALUES(1, 'Adam', '', 'Mickiewicz', '', '');

INSERT INTO authors(id, first_name, middle_name, last_name, monastery, title)
VALUES(2, 'Bolesław', '', 'Prus', '', '');

INSERT INTO authors(id, first_name, middle_name, last_name, monastery, title)
VALUES(3, 'Henryk', '', 'Sienkiewicz', '', '');

INSERT INTO authors(id, first_name, middle_name, last_name, monastery, title)
VALUES(7, 'Juliusz', '', 'Słowacki', '', '');

INSERT INTO authors(id, first_name, middle_name, last_name, monastery, title)
VALUES(8, 'Stefan', '', 'Żeromski', '', '');

INSERT INTO authors(id, first_name, middle_name, last_name, monastery, title)
VALUES(9, 'Władysław', '', 'Reymont', '', '');

INSERT INTO authors(id, first_name, middle_name, last_name, monastery, title)
VALUES(10, 'Zofia', '', 'Nałkowska', '', '');

-- Authors (Programming/Technical)
INSERT INTO authors(id, first_name, middle_name, last_name, monastery, title)
VALUES(5, 'Andrew', '', 'Hunt', '', '');

INSERT INTO authors(id, first_name, middle_name, last_name, monastery, title)
VALUES(6, 'David', '', 'Thomas', '', '');

INSERT INTO authors(id, first_name, middle_name, last_name, monastery, title)
VALUES(11, 'Robert', 'C.', 'Martin', '', '');

INSERT INTO authors(id, first_name, middle_name, last_name, monastery, title)
VALUES(12, 'Joshua', '', 'Bloch', '', '');

INSERT INTO authors(id, first_name, middle_name, last_name, monastery, title)
VALUES(13, 'Martin', '', 'Fowler', '', '');

INSERT INTO authors(id, first_name, middle_name, last_name, monastery, title)
VALUES(14, 'Eric', '', 'Evans', '', '');

INSERT INTO authors(id, first_name, middle_name, last_name, monastery, title)
VALUES(15, 'Vaughn', '', 'Vernon', '', '');

-- Authors (Classic/Modern Fiction)
INSERT INTO authors(id, first_name, middle_name, last_name, monastery, title)
VALUES(4, 'George', '', 'Orwell', '', '');

INSERT INTO authors(id, first_name, middle_name, last_name, monastery, title)
VALUES(16, 'J.R.R.', '', 'Tolkien', '', '');

INSERT INTO authors(id, first_name, middle_name, last_name, monastery, title)
VALUES(17, 'Jane', '', 'Austen', '', '');

INSERT INTO authors(id, first_name, middle_name, last_name, monastery, title)
VALUES(18, 'F. Scott', '', 'Fitzgerald', '', '');

INSERT INTO authors(id, first_name, middle_name, last_name, monastery, title)
VALUES(19, 'Gabriel', 'García', 'Márquez', '', '');

INSERT INTO authors(id, first_name, middle_name, last_name, monastery, title)
VALUES(20, 'Agatha', '', 'Christie', '', '');

INSERT INTO authors(id, first_name, middle_name, last_name, monastery, title)
VALUES(21, 'Isaac', '', 'Asimov', '', '');

INSERT INTO authors(id, first_name, middle_name, last_name, monastery, title)
VALUES(22, 'Philip', 'K.', 'Dick', '', '');

INSERT INTO authors(id, first_name, middle_name, last_name, monastery, title)
VALUES(23, 'Ursula', 'K.', 'Le Guin', '', '');

INSERT INTO authors(id, first_name, middle_name, last_name, monastery, title)
VALUES(24, 'Ray', '', 'Bradbury', '', '');

INSERT INTO authors(id, first_name, middle_name, last_name, monastery, title)
VALUES(25, 'Stephen', '', 'King', '', '');

INSERT INTO authors(id, first_name, middle_name, last_name, monastery, title)
VALUES(26, 'J.K.', '', 'Rowling', '', '');

INSERT INTO authors(id, first_name, middle_name, last_name, monastery, title)
VALUES(27, 'Neil', '', 'Gaiman', '', '');

INSERT INTO authors(id, first_name, middle_name, last_name, monastery, title)
VALUES(28, 'Margaret', '', 'Atwood', '', '');

INSERT INTO authors(id, first_name, middle_name, last_name, monastery, title)
VALUES(29, 'Haruki', '', 'Murakami', '', '');

INSERT INTO authors(id, first_name, middle_name, last_name, monastery, title)
VALUES(30, 'Kurt', '', 'Vonnegut', '', '');

-- Authors (Non-Fiction)
INSERT INTO authors(id, first_name, middle_name, last_name, monastery, title)
VALUES(31, 'Yuval Noah', '', 'Harari', '', '');

INSERT INTO authors(id, first_name, middle_name, last_name, monastery, title)
VALUES(32, 'Malcolm', '', 'Gladwell', '', '');

INSERT INTO authors(id, first_name, middle_name, last_name, monastery, title)
VALUES(33, 'Daniel', '', 'Kahneman', '', '');

INSERT INTO authors(id, first_name, middle_name, last_name, monastery, title)
VALUES(34, 'Viktor', 'E.', 'Frankl', '', 'Dr.');

INSERT INTO authors(id, first_name, middle_name, last_name, monastery, title)
VALUES(35, 'Carl', '', 'Sagan', '', 'Dr.');

INSERT INTO authors(id, first_name, middle_name, last_name, monastery, title)
VALUES(36, 'Stephen', '', 'Hawking', '', 'Dr.');

INSERT INTO authors(id, first_name, middle_name, last_name, monastery, title)
VALUES(37, 'Michelle', '', 'Obama', '', '');

INSERT INTO authors(id, first_name, middle_name, last_name, monastery, title)
VALUES(38, 'Erich', '', 'Gamma', '', '');

INSERT INTO authors(id, first_name, middle_name, last_name, monastery, title)
VALUES(39, 'Richard', '', 'Helm', '', '');

INSERT INTO authors(id, first_name, middle_name, last_name, monastery, title)
VALUES(40, 'Ralph', '', 'Johnson', '', '');

INSERT INTO authors(id, first_name, middle_name, last_name, monastery, title)
VALUES(41, 'John', '', 'Vlissides', '', '');

-- Series
INSERT INTO series (series_id, series_name, author_id, book_id) VALUES(1, 'Middle-earth', 16, NULL);
INSERT INTO series (series_id, series_name, author_id, book_id) VALUES(2, 'Foundation', 21, NULL);
INSERT INTO series (series_id, series_name, author_id, book_id) VALUES(3, 'Harry Potter', 26, NULL);

-- Books (Polish Literature)
INSERT INTO books(id, title, isbn, genre_id, category_id, series_id, pages_count, publication_year, reading_progress, publisher_id, monastery, notes)
VALUES(39, 'Cosmos', '978-0345331359', 2, NULL, NULL, 365, 1980, 'completed', 27, '', 'Introduction to cosmology for general audience. Carl Sagan explains complex topics beautifully. Inspiring and humbling.');

INSERT INTO books(id, title, isbn, genre_id, category_id, series_id, pages_count, publication_year, reading_progress, publisher_id, monastery, notes)
VALUES(42, 'A Brief History of Time', '978-0553380163', 2, NULL, NULL, 256, 1988, 'reading', 16, '', 'Hawking makes theoretical physics accessible. Black holes and time explained. Challenging but fascinating.');

INSERT INTO books(id, title, isbn, genre_id, category_id, series_id, pages_count, publication_year, reading_progress, publisher_id, monastery, notes)
VALUES(43, 'Becoming', '978-1524763138', 5, NULL, NULL, 448, 2018, 'completed', 28, '', 'Inspiring autobiography. Michelle Obama''s journey is remarkable. Honest and beautifully written.');

-- Book-Author relationships
INSERT INTO book_authors(book_id, author_id) VALUES(1, 1);
INSERT INTO book_authors(book_id, author_id) VALUES(2, 2);
INSERT INTO book_authors(book_id, author_id) VALUES(3, 5);
INSERT INTO book_authors(book_id, author_id) VALUES(3, 6);
INSERT INTO book_authors(book_id, author_id) VALUES(4, 1);
INSERT INTO book_authors(book_id, author_id) VALUES(5, 7);
INSERT INTO book_authors(book_id, author_id) VALUES(6, 8);
INSERT INTO book_authors(book_id, author_id) VALUES(7, 9);
INSERT INTO book_authors(book_id, author_id) VALUES(8, 10);
INSERT INTO book_authors(book_id, author_id) VALUES(9, 11);
INSERT INTO book_authors(book_id, author_id) VALUES(10, 11);
INSERT INTO book_authors(book_id, author_id) VALUES(11, 12);
INSERT INTO book_authors(book_id, author_id) VALUES(12, 13);
INSERT INTO book_authors(book_id, author_id) VALUES(13, 14);
INSERT INTO book_authors(book_id, author_id) VALUES(13, 15);
INSERT INTO book_authors(book_id, author_id) VALUES(14, 4);
INSERT INTO book_authors(book_id, author_id) VALUES(15, 4);
INSERT INTO book_authors(book_id, author_id) VALUES(16, 16);
INSERT INTO book_authors(book_id, author_id) VALUES(17, 16);
INSERT INTO book_authors(book_id, author_id) VALUES(18, 17);
INSERT INTO book_authors(book_id, author_id) VALUES(19, 18);
INSERT INTO book_authors(book_id, author_id) VALUES(20, 19);
INSERT INTO book_authors(book_id, author_id) VALUES(21, 20);
INSERT INTO book_authors(book_id, author_id) VALUES(22, 20);
INSERT INTO book_authors(book_id, author_id) VALUES(23, 21);
INSERT INTO book_authors(book_id, author_id) VALUES(24, 21);
INSERT INTO book_authors(book_id, author_id) VALUES(25, 22);
INSERT INTO book_authors(book_id, author_id) VALUES(26, 23);
INSERT INTO book_authors(book_id, author_id) VALUES(27, 24);
INSERT INTO book_authors(book_id, author_id) VALUES(28, 25);
INSERT INTO book_authors(book_id, author_id) VALUES(29, 25);
INSERT INTO book_authors(book_id, author_id) VALUES(30, 26);
INSERT INTO book_authors(book_id, author_id) VALUES(31, 27);
INSERT INTO book_authors(book_id, author_id) VALUES(32, 28);
INSERT INTO book_authors(book_id, author_id) VALUES(33, 29);
INSERT INTO book_authors(book_id, author_id) VALUES(34, 30);
INSERT INTO book_authors(book_id, author_id) VALUES(35, 31);
INSERT INTO book_authors(book_id, author_id) VALUES(36, 32);
INSERT INTO book_authors(book_id, author_id) VALUES(37, 33);
INSERT INTO book_authors(book_id, author_id) VALUES(38, 34);
INSERT INTO book_authors(book_id, author_id) VALUES(39, 35);
INSERT INTO book_authors(book_id, author_id) VALUES(40, 3);
INSERT INTO book_authors(book_id, author_id) VALUES(41, 38);
INSERT INTO book_authors(book_id, author_id) VALUES(41, 39);
INSERT INTO book_authors(book_id, author_id) VALUES(41, 40);
INSERT INTO book_authors(book_id, author_id) VALUES(41, 41);
INSERT INTO book_authors(book_id, author_id) VALUES(42, 36);
INSERT INTO book_authors(book_id, author_id) VALUES(43, 37);

-- Book-Topic relationships
INSERT INTO book_topics(book_id, topic_id) VALUES(1, 2);
INSERT INTO book_topics(book_id, topic_id) VALUES(2, 2);
INSERT INTO book_topics(book_id, topic_id) VALUES(3, 1);
INSERT INTO book_topics(book_id, topic_id) VALUES(4, 2);
INSERT INTO book_topics(book_id, topic_id) VALUES(5, 2);
INSERT INTO book_topics(book_id, topic_id) VALUES(6, 2);
INSERT INTO book_topics(book_id, topic_id) VALUES(7, 2);
INSERT INTO book_topics(book_id, topic_id) VALUES(7, 11);
INSERT INTO book_topics(book_id, topic_id) VALUES(8, 8);
INSERT INTO book_topics(book_id, topic_id) VALUES(9, 1);
INSERT INTO book_topics(book_id, topic_id) VALUES(10, 1);
INSERT INTO book_topics(book_id, topic_id) VALUES(11, 1);
INSERT INTO book_topics(book_id, topic_id) VALUES(12, 1);
INSERT INTO book_topics(book_id, topic_id) VALUES(13, 1);
INSERT INTO book_topics(book_id, topic_id) VALUES(14, 7);
INSERT INTO book_topics(book_id, topic_id) VALUES(14, 6);
INSERT INTO book_topics(book_id, topic_id) VALUES(15, 7);
INSERT INTO book_topics(book_id, topic_id) VALUES(16, 4);
INSERT INTO book_topics(book_id, topic_id) VALUES(17, 4);
INSERT INTO book_topics(book_id, topic_id) VALUES(18, 4);
INSERT INTO book_topics(book_id, topic_id) VALUES(19, 4);
INSERT INTO book_topics(book_id, topic_id) VALUES(20, 4);
INSERT INTO book_topics(book_id, topic_id) VALUES(21, 4);
INSERT INTO book_topics(book_id, topic_id) VALUES(22, 4);
INSERT INTO book_topics(book_id, topic_id) VALUES(23, 3);
INSERT INTO book_topics(book_id, topic_id) VALUES(24, 3);
INSERT INTO book_topics(book_id, topic_id) VALUES(25, 6);
INSERT INTO book_topics(book_id, topic_id) VALUES(26, 3);
INSERT INTO book_topics(book_id, topic_id) VALUES(27, 7);
INSERT INTO book_topics(book_id, topic_id) VALUES(28, 4);
INSERT INTO book_topics(book_id, topic_id) VALUES(29, 4);
INSERT INTO book_topics(book_id, topic_id) VALUES(30, 4);
INSERT INTO book_topics(book_id, topic_id) VALUES(31, 5);
INSERT INTO book_topics(book_id, topic_id) VALUES(31, 6);
INSERT INTO book_topics(book_id, topic_id) VALUES(32, 7);
INSERT INTO book_topics(book_id, topic_id) VALUES(33, 4);
INSERT INTO book_topics(book_id, topic_id) VALUES(34, 11);
INSERT INTO book_topics(book_id, topic_id) VALUES(35, 11);
INSERT INTO book_topics(book_id, topic_id) VALUES(35, 3);
INSERT INTO book_topics(book_id, topic_id) VALUES(36, 8);
INSERT INTO book_topics(book_id, topic_id) VALUES(36, 9);
INSERT INTO book_topics(book_id, topic_id) VALUES(37, 8);
INSERT INTO book_topics(book_id, topic_id) VALUES(38, 6);
INSERT INTO book_topics(book_id, topic_id) VALUES(38, 8);
INSERT INTO book_topics(book_id, topic_id) VALUES(39, 3);
INSERT INTO book_topics(book_id, topic_id) VALUES(40, 11);
INSERT INTO book_topics(book_id, topic_id) VALUES(40, 5);
INSERT INTO book_topics(book_id, topic_id) VALUES(41, 1);
INSERT INTO book_topics(book_id, topic_id) VALUES(42, 3);
INSERT INTO book_topics(book_id, topic_id) VALUES(43, 7);

INSERT INTO books(id, title, isbn, genre_id, category_id, series_id, pages_count, publication_year, reading_progress, publisher_id, monastery, notes)
VALUES(1, 'Pan Tadeusz', '978-83-08-02234-5', 3, 13, NULL, 148, 1834, 'unread', 1, '', 'National epic of Poland. Required reading for literature class.');

INSERT INTO books(id, title, isbn, genre_id, category_id, series_id, pages_count, publication_year, reading_progress, publisher_id, monastery, notes)
VALUES(2, 'Lalka', '978-83-06-02891-4', 1, 10, NULL, 695, 1890, 'reading', 2, '', 'Bolesław Prus masterpiece about unrequited love and social changes in 19th century Warsaw.');

INSERT INTO books(id, title, isbn, genre_id, category_id, series_id, pages_count, publication_year, reading_progress, publisher_id, monastery, notes)
VALUES(4, 'Dziady cz. III', '111-111-111', 4, 13, NULL, 190, 1832, 'unread', 1, '', 'Romantic drama about Polish struggle against Russian occupation.');

INSERT INTO books(id, title, isbn, genre_id, category_id, series_id, pages_count, publication_year, reading_progress, publisher_id, monastery, notes)
VALUES(5, 'Kordian', '222-222-222', 4, 13, NULL, 176, 1834, 'unread', 1, '', 'Juliusz Słowacki''s romantic drama about failed assassination attempt.');

INSERT INTO books(id, title, isbn, genre_id, category_id, series_id, pages_count, publication_year, reading_progress, publisher_id, monastery, notes)
VALUES(6, 'Przedwiośnie', '333-333-333', 1, 13, NULL, 320, 1924, 'reading', 2, '', 'Coming-of-age story set in turbulent times of early 20th century Poland.');

INSERT INTO books(id, title, isbn, genre_id, category_id, series_id, pages_count, publication_year, reading_progress, publisher_id, monastery, notes)
VALUES(7, 'Chłopi', '444-444-444', 1, 12, NULL, 900, 1904, 'completed', 2, '', 'Nobel Prize winner. Epic portrayal of Polish peasant life through four seasons.');

INSERT INTO books(id, title, isbn, genre_id, category_id, series_id, pages_count, publication_year, reading_progress, publisher_id, monastery, notes)
VALUES(8, 'Granica', '555-555-555', 1, 13, NULL, 300, 1935, 'unread', 2, '', 'Psychological novel exploring moral boundaries and human conscience.');

INSERT INTO books(id, title, isbn, genre_id, category_id, series_id, pages_count, publication_year, reading_progress, publisher_id, monastery, notes)
VALUES(40, 'Quo Vadis', '978-83-06-02345-2', 1, 12, NULL, 589, 1896, 'completed', 2, '', 'Nobel Prize winner. Epic historical novel set in ancient Rome during Nero''s persecution of Christians.');

-- Books (Programming/Technical)
INSERT INTO books(id, title, isbn, genre_id, category_id, series_id, pages_count, publication_year, reading_progress, publisher_id, monastery, notes)
VALUES(3, 'The Pragmatic Programmer', '978-0135957059', 2, 15, NULL, 352, 2019, 'completed', 3, '', 'Excellent insights on software design principles. Chapter on debugging is particularly useful. 20th Anniversary Edition.');

INSERT INTO books(id, title, isbn, genre_id, category_id, series_id, pages_count, publication_year, reading_progress, publisher_id, monastery, notes)
VALUES(9, 'Clean Code', '978-0132350884', 2, 15, NULL, 464, 2008, 'reading', 4, '', 'SOLID principles explained clearly. Good reference for code reviews. Uncle Bob at his best.');

INSERT INTO books(id, title, isbn, genre_id, category_id, series_id, pages_count, publication_year, reading_progress, publisher_id, monastery, notes)
VALUES(10, 'Clean Architecture', '978-0134494166', 2, 15, NULL, 432, 2017, 'unread', 5, '', 'Continuation of Clean Code principles applied to system architecture.');

INSERT INTO books(id, title, isbn, genre_id, category_id, series_id, pages_count, publication_year, reading_progress, publisher_id, monastery, notes)
VALUES(11, 'Effective Java', '978-0134685991', 2, 15, NULL, 416, 2018, 'completed', 3, '', 'Must-read for every Java developer. Third edition covers Java 7, 8, and 9.');

INSERT INTO books(id, title, isbn, genre_id, category_id, series_id, pages_count, publication_year, reading_progress, publisher_id, monastery, notes)
VALUES(12, 'Refactoring', '978-0201485677', 2, 15, NULL, 448, 1999, 'unread', 3, '', 'Classic book on improving code design. Need to check out the 2nd edition with JavaScript examples.');

INSERT INTO books(id, title, isbn, genre_id, category_id, series_id, pages_count, publication_year, reading_progress, publisher_id, monastery, notes)
VALUES(13, 'Domain-Driven Design', '978-0321125217', 2, 15, NULL, 560, 2003, 'reading', 3, '', 'DDD concepts are complex but worth understanding for enterprise software. Bounded contexts chapter is gold.');

INSERT INTO books(id, title, isbn, genre_id, category_id, series_id, pages_count, publication_year, reading_progress, publisher_id, monastery, notes)
VALUES(41, 'Design Patterns', '978-0201633610', 2, 15, NULL, 395, 1994, 'completed', 3, '', 'The Gang of Four classic. Essential patterns every developer should know.');

-- Books (Classic Fiction)
INSERT INTO books(id, title, isbn, genre_id, category_id, series_id, pages_count, publication_year, reading_progress, publisher_id, monastery, notes)
VALUES(14, '1984', '978-0451524935', 1, 7, NULL, 328, 1949, 'completed', 6, '', 'One of the most influential dystopian novels. More relevant today than ever. Compare with Brave New World.');

INSERT INTO books(id, title, isbn, genre_id, category_id, series_id, pages_count, publication_year, reading_progress, publisher_id, monastery, notes)
VALUES(15, 'Animal Farm', '978-0451526342', 1, 4, NULL, 141, 1945, 'completed', 7, '', 'Brilliant political allegory. Short but powerful critique of totalitarianism.');

INSERT INTO books(id, title, isbn, genre_id, category_id, series_id, pages_count, publication_year, reading_progress, publisher_id, monastery, notes)
VALUES(16, 'The Lord of the Rings', '978-0544003415', 1, 5, 1, 1178, 1954, 'reading', 8, '', 'The trilogy is incredible. Currently on Fellowship. Need to read The Two Towers and Return of the King next.');

INSERT INTO books(id, title, isbn, genre_id, category_id, series_id, pages_count, publication_year, reading_progress, publisher_id, monastery, notes)
VALUES(17, 'The Hobbit', '978-0547928227', 1, 5, 1, 310, 1937, 'completed', 9, '', 'Read this before starting The Lord of the Rings for better context. Delightful adventure story.');

INSERT INTO books(id, title, isbn, genre_id, category_id, series_id, pages_count, publication_year, reading_progress, publisher_id, monastery, notes)
VALUES(18, 'Pride and Prejudice', '978-0141439518', 1, 10, NULL, 279, 1813, 'completed', 10, '', 'Jane Austen''s wit and social commentary are timeless. Elizabeth Bennet is one of literature''s best heroines.');

INSERT INTO books(id, title, isbn, genre_id, category_id, series_id, pages_count, publication_year, reading_progress, publisher_id, monastery, notes)
VALUES(19, 'The Great Gatsby', '978-0743273565', 1, 13, NULL, 180, 1925, 'completed', 11, '', 'Beautiful prose. The American Dream dissected. Short but impactful.');

INSERT INTO books(id, title, isbn, genre_id, category_id, series_id, pages_count, publication_year, reading_progress, publisher_id, monastery, notes)
VALUES(20, 'One Hundred Years of Solitude', '978-0060883287', 1, 13, NULL, 417, 1967, 'reading', 12, '', 'Magical realism at its finest. The Buendía family saga is mesmerizing but need to keep track of all the names!');

-- Books (Mystery)
INSERT INTO books(id, title, isbn, genre_id, category_id, series_id, pages_count, publication_year, reading_progress, publisher_id, monastery, notes)
VALUES(21, 'Murder on the Orient Express', '978-0062693662', 1, 3, NULL, 256, 1934, 'completed', 13, '', 'Classic mystery with an unforgettable ending. Agatha Christie at her best. The solution is ingenious.');

INSERT INTO books(id, title, isbn, genre_id, category_id, series_id, pages_count, publication_year, reading_progress, publisher_id, monastery, notes)
VALUES(22, 'And Then There Were None', '978-0062073488', 1, 11, NULL, 272, 1939, 'completed', 13, '', 'Christie''s masterpiece. Ten strangers trapped on an island. Perfectly constructed mystery.');

-- Books (Science Fiction)
INSERT INTO books(id, title, isbn, genre_id, category_id, series_id, pages_count, publication_year, reading_progress, publisher_id, monastery, notes)
VALUES(23, 'Foundation', '978-0553293357', 1, 7, 2, 255, 1951, 'reading', 14, '', 'Foundation series starter. Asimov''s psychohistory concept is brilliant. Can''t wait to read the sequels.');

INSERT INTO books(id, title, isbn, genre_id, category_id, series_id, pages_count, publication_year, reading_progress, publisher_id, monastery, notes)
VALUES(24, 'I, Robot', '978-0553382563', 1, 7, NULL, 224, 1950, 'completed', 15, '', 'The Three Laws of Robotics introduced here. Must-read sci-fi. Stories are still relevant today.');

INSERT INTO books(id, title, isbn, genre_id, category_id, series_id, pages_count, publication_year, reading_progress, publisher_id, monastery, notes)
VALUES(25, 'Do Androids Dream of Electric Sheep?', '978-0345404473', 1, 7, NULL, 210, 1968, 'completed', 16, '', 'Basis for Blade Runner. Philip K. Dick explores what it means to be human. Mind-bending.');

INSERT INTO books(id, title, isbn, genre_id, category_id, series_id, pages_count, publication_year, reading_progress, publisher_id, monastery, notes)
VALUES(26, 'The Left Hand of Darkness', '978-0441478125', 1, 7, NULL, 304, 1969, 'unread', 16, '', 'Ursula K. Le Guin''s exploration of gender and society on an alien world.');

INSERT INTO books(id, title, isbn, genre_id, category_id, series_id, pages_count, publication_year, reading_progress, publisher_id, monastery, notes)
VALUES(27, 'Fahrenheit 451', '978-1451673319', 1, 7, NULL, 249, 1953, 'completed', 16, '', 'Prescient warning about censorship and the death of critical thinking. Bradbury was ahead of his time.');

-- Books (Horror)
INSERT INTO books(id, title, isbn, genre_id, category_id, series_id, pages_count, publication_year, reading_progress, publisher_id, monastery, notes)
VALUES(28, 'The Shining', '978-0307743657', 1, 6, NULL, 447, 1977, 'completed', 15, '', 'Terrifying and compelling. King''s best work in my opinion. The Overlook Hotel is unforgettable.');

INSERT INTO books(id, title, isbn, genre_id, category_id, series_id, pages_count, publication_year, reading_progress, publisher_id, monastery, notes)
VALUES(29, 'It', '978-1501142970', 1, 6, NULL, 1138, 1986, 'reading', 17, '', 'Epic horror spanning decades. Pennywise is terrifying. Long but worth it.');

-- Books (Fantasy)
INSERT INTO books(id, title, isbn, genre_id, category_id, series_id, pages_count, publication_year, reading_progress, publisher_id, monastery, notes)
VALUES(30, 'Harry Potter and the Philosopher''s Stone', '978-0439708180', 1, 5, 3, 309, 1997, 'completed', 18, '', 'Started the series with my kids. Magical and well-written. The beginning of an incredible journey.');

INSERT INTO books(id, title, isbn, genre_id, category_id, series_id, pages_count, publication_year, reading_progress, publisher_id, monastery, notes)
VALUES(31, 'American Gods', '978-0060558123', 1, 5, NULL, 465, 2001, 'reading', 19, '', 'Neil Gaiman''s mythology meets modern America. Shadow''s journey is fascinating.');

INSERT INTO books(id, title, isbn, genre_id, category_id, series_id, pages_count, publication_year, reading_progress, publisher_id, monastery, notes)
VALUES(32, 'The Handmaid''s Tale', '978-0385490818', 1, 7, NULL, 311, 1985, 'completed', 20, '', 'Dystopian classic that feels uncomfortably relevant. Atwood''s prose is haunting.');

INSERT INTO books(id, title, isbn, genre_id, category_id, series_id, pages_count, publication_year, reading_progress, publisher_id, monastery, notes)
VALUES(33, 'Norwegian Wood', '978-0375704024', 1, 13, NULL, 296, 1987, 'completed', 21, '', 'Murakami''s most accessible novel. Melancholic and beautiful. The 1960s Tokyo setting is vivid.');

INSERT INTO books(id, title, isbn, genre_id, category_id, series_id, pages_count, publication_year, reading_progress, publisher_id, monastery, notes)
VALUES(34, 'Slaughterhouse-Five', '978-0385333849', 1, 7, NULL, 275, 1969, 'completed', 22, '', 'Vonnegut''s anti-war masterpiece. So it goes. The time-jumping narrative is brilliant.');

-- Books (Non-Fiction)
INSERT INTO books(id, title, isbn, genre_id, category_id, series_id, pages_count, publication_year, reading_progress, publisher_id, monastery, notes)
VALUES(35, 'Sapiens', '978-0062316097', 2, NULL, NULL, 443, 2011, 'completed', 23, '', 'Fascinating look at human history. Changed my perspective on civilization. Harari connects past to present brilliantly.');

INSERT INTO books(id, title, isbn, genre_id, category_id, series_id, pages_count, publication_year, reading_progress, publisher_id, monastery, notes)
VALUES(36, 'Outliers', '978-0316017930', 2, NULL, NULL, 309, 2008, 'completed', 24, '', 'The 10,000 hour rule is discussed here. Very insightful on success. Gladwell''s storytelling makes research engaging.');

INSERT INTO books(id, title, isbn, genre_id, category_id, series_id, pages_count, publication_year, reading_progress, publisher_id, monastery, notes)
VALUES(37, 'Thinking, Fast and Slow', '978-0374533557', 2, NULL, NULL, 499, 2011, 'reading', 25, '', 'System 1 vs System 2 thinking - must remember this framework. Dense but rewarding. Kahneman''s research is groundbreaking.');

INSERT INTO books(id, title, isbn, genre_id, category_id, series_id, pages_count, publication_year, reading_progress, publisher_id, monastery, notes)
VALUES(38, 'Man''s Search for Meaning', '978-0807014295', 2, NULL, NULL, 165, 1946, 'completed', 26, '', 'Powerful memoir about finding purpose even in suffering. Frankl''s logotherapy is profound. Life-changing book.');
