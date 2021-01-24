package ru.specialist.hello.java.Java2.jdbc;

import java.sql.*;
import java.util.Scanner;

/**
 * Класс описывающий вызов процедуры из Postgresql
 */

public class ProcedureExample {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String LOGIN = "postgres";
    private static final String PASSWORD = "UgolnikoVa77792";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        System.out.println("Create new author (myself)");

        try (Connection c = DriverManager.getConnection(URL,LOGIN,PASSWORD)) {
            CallableStatement call = c.prepareCall("call insert_author (?, ?)");
            call.setString(1, "Petr");
            call.setString(2, "Petrov");
            call.execute();

        }

    }
}
//1.30
