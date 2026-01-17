insert into genres (id, name) values(1, 'fiction');
insert into genres (id, name) values(2, 'non-fiction');
insert into genres (id, name) values(3, 'poetry');
insert into genres (id, name) values(4, 'drama');

insert into categories(id, name, is_custom) values(1, 'Action and adventure', 'F');
insert into categories(id, name, is_custom) values(2, 'Comedy', 'F');
insert into categories(id, name, is_custom) values(3, 'Crime and mystery', 'F');
insert into categories(id, name, is_custom) values(4, 'Speculative fiction', 'F');
insert into categories(id, name, is_custom) values(5, 'Fantasy', 'F');
insert into categories(id, name, is_custom) values(6, 'Horror', 'F');
insert into categories(id, name, is_custom) values(7, 'Science fiction', 'F');
insert into categories(id, name, is_custom) values(8, 'Science fantasy', 'F');
insert into categories(id, name, is_custom) values(9, 'Superhero', 'F');
insert into categories(id, name, is_custom) values(10, 'Romance', 'F');

insert into authors(id, first_name, middle_name, last_name, monastery, title)
values(1, "Adam", "", "Mickiewicz", "", "");

insert into authors(id, first_name, middle_name, last_name, monastery, title)
values(2, "Bolesław", "", "Prus", "", "");

insert into authors(id, first_name, middle_name, last_name, monastery, title)
values(3, "Henryk", "", "Sienkiewicz", "", "");

insert into authors(id, first_name, middle_name, last_name, monastery, title)
values(4, "George", "", "Orwell", "", "");

insert into authors(id, first_name, middle_name, last_name, monastery, title)
values(5, "Andrew", "", "Hunt", "", "");

insert into authors(id, first_name, middle_name, last_name, monastery, title)
values(6, "David", "", "Thomas", "", "");

insert into authors(id, first_name, middle_name, last_name, monastery, title)
values(7, 'Juliusz', '', 'Słowacki', '', '');

insert into authors(id, first_name, middle_name, last_name, monastery, title)
values(8, 'Stefan', '', 'Żeromski', '', '');

insert into authors(id, first_name, middle_name, last_name, monastery, title)
values(9, 'Władysław', '', 'Reymont', '', '');

insert into authors(id, first_name, middle_name, last_name, monastery, title)
values(10, 'Zofia', '', 'Nałkowska', '', '');

insert into authors(id, first_name, middle_name, last_name, monastery, title)
values(11, 'Robert', 'C.', 'Martin', '', '');

insert into authors(id, first_name, middle_name, last_name, monastery, title)
values(12, 'Joshua', '', 'Bloch', '', '');

insert into authors(id, first_name, middle_name, last_name, monastery, title)
values(13, 'Martin', '', 'Fowler', '', '');

insert into authors(id, first_name, middle_name, last_name, monastery, title)
values(14, 'Eric', '', 'Evans', '', '');

insert into authors(id, first_name, middle_name, last_name, monastery, title)
values(15, 'Vaughn', '', 'Vernon', '', '');

insert into books(id, title, isbn, genre_id, series_id, pages_count, publication_year, reading_progress)
values(1, "Pan Tadeusz", "1234-1234-234", 1, null, 148, 1967, 'unread');

insert into books(id, title, isbn, genre_id, series_id, pages_count, publication_year, reading_progress)
values(2, "Lalka", "1234-1234-234", 1, null, 149, 1967, 'reading');

insert into books(id, title, isbn, genre_id, series_id, pages_count, publication_year, reading_progress)
values(3, "The Pragmatic Programmer", "326-326-326", 2, null, 150, 2015, 'completed');

insert into books(id, title, isbn, genre_id, series_id, pages_count, publication_year, reading_progress)
values(4, 'Dziady cz. III', '111-111-111', 4, null, 190, 1832, 'unread');

insert into books(id, title, isbn, genre_id, series_id, pages_count, publication_year, reading_progress)
values(5, 'Kordian', '222-222-222', 4, null, 176, 1834, 'unread');

insert into books(id, title, isbn, genre_id, series_id, pages_count, publication_year, reading_progress)
values(6, 'Przedwiośnie', '333-333-333', 1, null, 320, 1924, 'reading');

insert into books(id, title, isbn, genre_id, series_id, pages_count, publication_year, reading_progress)
values(7, 'Chłopi', '444-444-444', 1, null, 900, 1904, 'completed');

insert into books(id, title, isbn, genre_id, series_id, pages_count, publication_year, reading_progress)
values(8, 'Granica', '555-555-555', 1, null, 300, 1935, 'unread');

insert into books(id, title, isbn, genre_id, series_id, pages_count, publication_year, reading_progress)
values(9, 'Clean Code', '978-0132350884', 2, null, 464, 2008, 'reading');

insert into books(id, title, isbn, genre_id, series_id, pages_count, publication_year, reading_progress)
values(10, 'Clean Architecture', '978-0134494166', 2, null, 432, 2017, 'unread');

insert into books(id, title, isbn, genre_id, series_id, pages_count, publication_year, reading_progress)
values(11, 'Effective Java', '978-0134685991', 2, null, 416, 2018, 'completed');

insert into books(id, title, isbn, genre_id, series_id, pages_count, publication_year, reading_progress)
values(12, 'Refactoring', '978-0201485677', 2, null, 448, 1999, 'unread');

insert into books(id, title, isbn, genre_id, series_id, pages_count, publication_year, reading_progress)
values(13, 'Domain-Driven Design', '978-0321125217', 2, null, 560, 2003, 'reading');

insert into book_authors(author_id, book_id) values(1, 1);
insert into book_authors(author_id, book_id) values(2, 2);
insert into book_authors(author_id, book_id) values(5, 3);
insert into book_authors(author_id, book_id) values(6, 3);
insert into book_authors(author_id, book_id) values(1, 1);
insert into book_authors(author_id, book_id) values(1, 4);
insert into book_authors(author_id, book_id) values(7, 5);
insert into book_authors(author_id, book_id) values(8, 6);
insert into book_authors(author_id, book_id) values(9, 7);
insert into book_authors(author_id, book_id) values(10, 8);
insert into book_authors(author_id, book_id) values(11, 9);
insert into book_authors(author_id, book_id) values(11, 10);
insert into book_authors(author_id, book_id) values(12, 11);
insert into book_authors(author_id, book_id) values(13, 12);
insert into book_authors(author_id, book_id) values(14, 13);
insert into book_authors(author_id, book_id) values(13, 9);
insert into book_authors(author_id, book_id) values(14, 10);
insert into book_authors(author_id, book_id) values(15, 13);
insert into book_authors(author_id, book_id)values (13, 9);
insert into book_authors(author_id, book_id) values (14, 10);

