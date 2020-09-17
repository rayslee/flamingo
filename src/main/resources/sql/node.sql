-------------------------------- TABLE User --------------------------
delete from User;
insert into User (id, username, password) values (1910, 'Flamingo', '294f2e85f571580670cfc5f29a0a040db9b4fc710dfb222726dfb2e1ed3f84e1dbfbde85c2cb6afb');

-------------------------------- TABLE NODE --------------------------
delete from Node;
insert into Node (id, name) values (1910, 'Flamingo');
insert into Node (id, name, parent_id) values (1911, 'Leopard', 1910);
insert into Node (id, name, parent_id) values (1912, 'Caribou', 1910);
insert into Node (id, name, parent_id) values (1913, 'Giraff', 1910);


------------------------------------- TABLE NODE_ATTRIBUTES ----------------------------------
delete from Node_Attributes;
insert into Node_Attributes (node_id, attributes_key, attributes) values (1910, 'How', 'Brain');
insert into Node_Attributes (node_id, attributes_key, attributes) values (1910, 'Why', 'Reason');
insert into Node_Attributes (node_id, attributes_key, attributes) values (1910, 'Yum', 'Cheese');


------------------------- TABLE NODE_MODULES ----------------------
delete from Node_Modules;
insert into Node_Modules (node_id, modules) values (1910, 'LIBRARY');
insert into Node_Modules (node_id, modules) values (1910, 'INDEX');

---------------------------------------------- TABLE BOOK ------------------------------------
delete from Book;
insert into Book (isbn, title) values ('978-0-13-468599-1', 'Effective Java');
insert into Book (isbn, title) values ('978-1-78528-612-4', 'Apache Maven Cookbook');
insert into Book (isbn, title) values ('978-1-61729-356-6', 'Modern Java in Action');
insert into Book (isbn, title) values ('978-1-61729-045-9', 'Java Persistence with Hibernate');
insert into Book (isbn, title) values ('978-1-61729-494-5', 'Spring in Action');

------------------------------- TABLE BOOK_LINK -----------------------------
delete from Book_Link;
insert into Book_Link (depot_id, book_id) values (1910, '978-0-13-468599-1');
insert into Book_Link (depot_id, book_id) values (1910, '978-1-78528-612-4');
insert into Book_Link (depot_id, book_id) values (1910, '978-1-61729-356-6');


------------------------ TABLE AUTHOR ------------------
delete from Author;
insert into Author (id, name) values (2001, 'Joshua');
insert into Author (id, name) values (2002, 'Raghuram');
insert into Author (id, name) values (2003, 'Raoul');
insert into Author (id, name) values (2004, 'Gavin');
insert into Author (id, name) values (2005, 'Craig');

------------------------------- TABLE AUTHOR_LINK -------------------------------
delete from Author_Link;
insert into Author_Link (book_id, author_id) values ('978-0-13-468599-1', 2001);
insert into Author_Link (book_id, author_id) values ('978-1-78528-612-4', 2002);
insert into Author_Link (book_id, author_id) values ('978-1-61729-356-6', 2003);
insert into Author_Link (book_id, author_id) values ('978-1-61729-045-9', 2004);
insert into Author_Link (book_id, author_id) values ('978-1-61729-494-5', 2005);

