delete from Book;
insert into Book (isbn, title) values ('978-0-13-468599-1', 'Effective Java');
insert into Book (isbn, title) values ('978-1-78528-612-4', 'Apache Maven Cookbook');
insert into Book (isbn, title) values ('978-1-61729-356-6', 'Modern Java in Action');
insert into Book (isbn, title) values ('978-1-61729-045-9', 'Java Persistence with Hibernate');
insert into Book (isbn, title) values ('978-1-61729-494-5', 'Spring in Action');
insert into Book (isbn, title) values ('978-1-48421-702-3', 'Harley Hahn.s Emacs Field Guide');
insert into Book (isbn, title) values ('978-0-07313-361-4', 'Harley Hahn.s Guide to Unix and Linux');
insert into Book (isbn, title) values ('978-0-07025-492-3', 'Harley Hahn.s Student Guide To Unix');
insert into Book (isbn, title) values ('978-0-07222-553-2', 'Harley Hahn.s Internet Yellow Pages');
insert into Book (isbn, title) values ('978-0-13033-448-0', 'Harley Hahn.s Internet Insecurity');
insert into Book (isbn, title) values ('978-0-78972-093-1', 'Harley Hahn Teaches the Internet');
insert into Book (isbn, title) values ('978-0-78972-697-1', 'Harley Hahn.s Internet Advisor');

insert into Node_Modules (node_id, modules) values (1984, 'LIBRARY');

delete from Book_Link;
insert into Book_Link (depot_id, book_id) values (1984, '978-0-13-468599-1');
insert into Book_Link (depot_id, book_id) values (1984, '978-1-78528-612-4');
insert into Book_Link (depot_id, book_id) values (1984, '978-1-61729-356-6');
insert into Book_Link (depot_id, book_id) values (1984, '978-1-48421-702-3');
insert into Book_Link (depot_id, book_id) values (1984, '978-0-07313-361-4');
insert into Book_Link (depot_id, book_id) values (1984, '978-0-07025-492-3');
insert into Book_Link (depot_id, book_id) values (1984, '978-0-07222-553-2');
insert into Book_Link (depot_id, book_id) values (1984, '978-0-13033-448-0');
insert into Book_Link (depot_id, book_id) values (1984, '978-0-78972-093-1');
insert into Book_Link (depot_id, book_id) values (1984, '978-0-78972-697-1');