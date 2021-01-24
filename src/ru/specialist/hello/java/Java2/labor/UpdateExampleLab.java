package ru.specialist.hello.java.Java2.labor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class UpdateExampleLab {
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
        System.out.println("Create new author (myself)");

        try (Connection c = DriverManager.getConnection(URL,LOGIN,PASSWORD);
        Scanner scanner = new Scanner(System.in)) {

            PreparedStatement statement = c.prepareStatement("insert into authors " +
                    "(author_name, last_name) " +
                    "values ( ?, ?)");

            System.out.println("Write last Name");
            String param = scanner.nextLine();
            statement.setString(2, param);
            System.out.println("Write the name of Author");
            param = scanner.nextLine();
            statement.setString(1, param);
            statement.executeUpdate();


        }

    }
}
//1.30
