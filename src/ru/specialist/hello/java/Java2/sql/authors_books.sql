create table authors(
    author_id serial primary key,
    author_name varchar(250),
    last_name varchar(250)
);

create table books(
    book_id serial primary key,
    title text,
    author_id integer REFERENCES authors
);

--insert into authors (author_id, author_name, last_name) values (1, 'Бьёрн', 'Страуструп');
insert into authors (author_name, last_name) values ('Бьёрн', 'Страуструп');
insert into authors (author_name, last_name) values ('Брюс', 'Эккель');
insert into authors (author_name, last_name) values ('Блох', 'Джошуа');
insert into authors (author_name, last_name) values ('Дональд', 'Кнут');
insert into authors (author_name, last_name) values ('Роберт', 'Мартин');
insert into authors (author_name, last_name) values ('Джон', 'Скит');

insert into books (title, author_id) values ('Язык программирования C++', 1);
insert into books (title, author_id) values ('Java Concurrency на практике', 3);
insert into books (title, author_id) values ('Java. Эффективное программирование', 3);
insert into books (title, author_id) values ('Философия Java', 2);
insert into books (title, author_id) values ('Дизайн и эволюция языка C++', 1);
insert into books (title, author_id) values ('Искусство программирования', 4);
insert into books (title, author_id) values ('Философия C++. Введение в стандартный C++', 2);
insert into books (title, author_id) values ('C# для профессионалов', 6);
insert into books (title, author_id) values ('Чистый код. Создание, анализ и рефакторинг', 5);
insert into books (title, author_id) values ('Гибкая разработка программ на Java и C++. Принципы, паттерны и методики', 5);