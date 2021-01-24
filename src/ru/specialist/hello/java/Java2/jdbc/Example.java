package ru.specialist.hello.java.Java2.jdbc;

import java.sql.*;
import java.util.Iterator;
import java.util.Scanner;

public class Example {
    /**
     * Создаем приватные финальные данные для подключения к БД
     * Эти переменные могут содержать, пароли, имена, какие-либо строки и тд.
     * Все, что нужно для подключения к БД
     * Для подключения к БД нужно скачать jar файл с сайта mvn(maven) тк это наш сборщик проекта
     * все файлы jar находятся в папке lib. Потом подключаем этот драйвер:
     * File\Project Structure\Libraries\+\Java\.jar
     */
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String LOGIN = "postgres";
    private static final String PASSWORD = "UgolnikoVa77792";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        /**
         * Подключение драйвера через класс postgres. ЭТО НЕ ПОДКЛЮЧЕИЕ!
         * Можно сделать двумя способами. Первый лучше,
         * тк не завязан на строках.
         * 1. Мы прописываем сами
         * 2. Берем с оф. сайта.
         */
//        Class.forName(Driver.class.getCanonicalName());
        Class.forName("org.postgresql.Driver");

        //Подключение к бд c автоматическим закрытием после выполнения кода
        try (Connection c = DriverManager.getConnection(URL,LOGIN,PASSWORD);
        /*Создадим сканер для поиска из задачи 3*/

        Scanner scanner = new Scanner(System.in)){

            String param = scanner.nextLine();

            /**
             * Выполнение запросов.
             * Используем интерфейс Statement
             * После выполнения запроса нам возвращается объект в виде
             * интерфейса ResultSet
             * Пока закомментирован, чтобы выполнить восстановление БД
             */
//            Statement statement = c.createStatement();
//
//            System.out.println("--------Task 1: Print author_id & Name--------");
//            ResultSet set = statement.executeQuery("select author_id, author_name" +
//                                                   ", last_name from authors;");
//            while (set.next()){
//                int authorId = set.getInt(1);
//                String authorName = set.getString(2);
//                String lastName = set.getString(3);
//                System.out.printf("Author: %d, %s %s\n", authorId, authorName, lastName );
//            }
//            System.out.printf("----------------------\n");
//
//            System.out.println("--------Task 2: Print books with JAVA--------");
//            ResultSet java = statement.executeQuery("select book_id, " +
//                                                    "title from books \n" +
//            /* лучше приводить к регистру вручную*/       "where upper (title) like '%JAVA%'");
////          /* это статический поиск без сканера*/        "where title like '%Java%'");
//            while (java.next()){
//                int bookId = java.getInt(1);
//                String title = java.getString(2);
//                System.out.printf("Book: %d. %s\n", bookId, title);
//            }

/**
 * Запрещенные запросы
 * "where title like '%" + param + "%'"
 * Так передавать запросы нельзя, тк можно сломать бд
 */
            System.out.println("--------Task 3: CRASH Data--------");
//
//            ResultSet java1 = statement.executeQuery("select book_id, " +
//                            "title from books\n" +
//                            "where title like '%" +
//                            param + "%'"); /* Это для вывода запросов через сканер*/
////                                                    "where title like '%Java%'");
//
//            while (java1.next()){
//                int bookId = java1.getInt(1);
//                String title = java1.getString(2);
//                System.out.printf("Book: %d. %s\n", bookId, title);
//            }

            /**
             * Безопасный запрос
             * where title like ? - вместо ? вставляется эта строка ->
             * statement2.setString - делает вводимую строку не как запрос,
             * а как поиск слова в строке, тем самым не дает разрушить БД
             * индекс 1 указывает, что введенные данные вставляются в первый "?"
             */
            System.out.println("--------Task 4: Protect Statement Data--------");

            PreparedStatement statement = c.prepareStatement("select book_id,title " +
                                                             "from books " +
                                                             "where title like ? " +
                                                             "and title like ?");

            statement.setString(1,"%" + param + "%");
            param = scanner.nextLine(); //для второго запроса
            statement.setString(2,"%" + param + "%");

            ResultSet java1 = statement.executeQuery();

            while (java1.next()){
//                int bookId = java1.getInt(1);
                int bookId = java1.getInt("book_id"); //Строковое представление
//                String title = java1.getString(2);
                String title = java1.getString("title");//строковое представление
                System.out.printf("Book: %d. %s\n", bookId, title);
        }

        }
    }
}
//1.30
