-- Процедура создания автора
create or replace procedure insert_author(varchar, varchar)
language plpgsql
as $$
begin
	insert into authors(author_name, last_name) values ($1, $2);
end;
$$;

call insert_author('Ivan', 'Ivanov');

--Функция вывода числа и прибавление 1
create or replace function inc(integer) returns integer
language plpgsql
as $$
begin
	return $1 + 1;
end;
$$;

select inc(1);

select inc(book_id), title from books;
--Функция создания автора с возвратом присвоенного id
create or replace function insert_author_returning_key(varchar, varchar) returns integer
language plpgsql
as $$
declare
	a_id integer;
begin
	insert into authors(author_name, last_name)
		values ($1, $2)
		returning author_id into a_id;
	return a_id;
end;
$$;

select insert_author_returning_key('111', '111');
--Функция создания автора и книги с возвратом присвоенного book_id
create or replace function insert_author_and_book (varchar, varchar, varchar) returns integer
language plpgsql
as $$
declare
	a_id integer;
	b_id integer;
begin
	insert into authors(author_name, last_name)
		values ($1, $2)
		returning author_id into a_id;
	insert into books(title, author_id)
		values ($3, a_id)
		returning book_id into b_id;
	return b_id;
end;
$$;

select insert_author_and_book('Виктор', 'Бодров', 'Удивительная Java');
//---2,56