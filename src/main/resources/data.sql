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

insert into books(id, title, isbn, genre_id, series_id, pages_count, publication_year, reading_progress, monastery)
values(1, "Pan Tadeusz", "1234-1234-234", 1, null, 148, 1967, 'unread', 'value');

insert into books(id, title, isbn, genre_id, series_id, pages_count, publication_year, reading_progress, monastery)
values(2, "Lalka", "1234-1234-234", 1, null, 149, 1967, 'reading', 'tyniec');

insert into books(id, title, isbn, genre_id, series_id, pages_count, publication_year, reading_progress, monastery)
values(3, "The Pragmatic Programmer", "326-326-326", 2, null, 150, 2015, 'completed', 'tyniec 2');

insert into book_authors(author_id, book_id) values(1, 1);
insert into book_authors(author_id, book_id) values(2, 2);
insert into book_authors(author_id, book_id) values(5, 3);
insert into book_authors(author_id, book_id) values(6, 3);
