package ru.specialist.hello.java.Java2.jdbc;

import java.sql.*;
import java.util.Scanner;

public class UpdateExample {
    /**
     * Statement.RETURN_GENERATED_KEYS - команда для вывода id-шника
     */
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String LOGIN = "postgres";
    private static final String PASSWORD = "UgolnikoVa77792";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        System.out.println("Create a new book (myself)");

        try (Connection c = DriverManager.getConnection(URL,LOGIN,PASSWORD)) {

            PreparedStatement statement = c.prepareStatement("insert into books " +
                    "(title, author_id) " +
                    "values ( ?, ?)", Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, "New book");
            statement.setInt(2, 1);
            statement.executeUpdate();

            ResultSet set = statement.getGeneratedKeys();

            while (set.next()){
                System.out.println("Generated book_id: " + set.getInt(1));
            }
        }

    }
}
//1.30
