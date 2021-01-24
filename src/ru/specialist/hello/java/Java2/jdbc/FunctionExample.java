package ru.specialist.hello.java.Java2.jdbc;

import java.sql.*;
import java.util.Scanner;

/**
 * Класс описывающий вызов функции из Postgresql (инкремент)
 * Отличается синтаксисом при вызове
 */

public class FunctionExample {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String LOGIN = "postgres";
    private static final String PASSWORD = "UgolnikoVa77792";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        try (Connection c = DriverManager.getConnection(URL,LOGIN,PASSWORD);
             Scanner scanner = new Scanner(System.in)) {

            CallableStatement call = c.prepareCall("{? = call inc (?)}");
//            call.registerOutParameter(1, Types.INTEGER);
//            call.setInt(2, 9);
//            call.execute();
//            System.out.println(call.getInt(1));
//
//            call = c.prepareCall("{? = call insert_author_returning_key(?, ?)}");
//            call.registerOutParameter(1, Types.INTEGER);
//            call.setString(2, "Vasya");
//            call.setString(3, "Petrov");
//            call.execute();
//            System.out.println(call.getInt(1));

            call = c.prepareCall("{? = call insert_author_and_book(?, ?, ?)}");
            call.registerOutParameter(1, Types.INTEGER);

            System.out.println("Enter first name : ");
            String firstName = scanner.nextLine();
            System.out.println("Enter second name : ");
            String secondName = scanner.nextLine();
            System.out.println("Enter title : ");
            String title = scanner.nextLine();

            call.setString(2, firstName);
            call.setString(3, secondName);
            call.setString(4, title);
            call.execute();
            System.out.println(call.getInt(1));
        }

    }
}

