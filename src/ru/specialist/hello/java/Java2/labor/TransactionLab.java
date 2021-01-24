package ru.specialist.hello.java.Java2.labor;

import java.sql.*;
import java.util.Scanner;

/**
 * Лабораторка на создание запросов и выполнение их одной транзакцией
 * (куска лекции нет)
 * c.setAutoCommit(true) - в положении true все операторы SQL выполняются отдельно
 * в положении false операторы будут выполняться одной транзакцией после команды
 * c.commit();
 * Statement.RETURN_GENERATED_KEYS - генерирует id
 */
public class TransactionLab {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String LOGIN = "postgres";
    private static final String PASSWORD = "UgolnikoVa77792";

    public static void main(String[] args){

        try(Connection c = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            Scanner scanner = new Scanner(System.in))
        {
            c.setAutoCommit(false);

            int authorId = 0;
            int bookId = 0;

            try{
                PreparedStatement statement = c.prepareStatement("insert into authors\n"+
                        "(author_name, last_name)\n"+
                        "values (?, ?)", Statement.RETURN_GENERATED_KEYS);

                System.out.println("Enter author name:");
                String fistName = scanner.nextLine();
                statement.setString(1, fistName);

                System.out.println("Enter author last name:");
                String lastName = scanner.nextLine();
                statement.setString(2, lastName);

                System.out.println("Enter book title:");
                String title = scanner.nextLine();
                statement.executeUpdate();
                ResultSet set = statement.getGeneratedKeys();
                while (set.next()){
                    authorId = set.getInt(1);
                    System.out.println("Generated author_id: " + authorId);
                }

                statement = c.prepareStatement("insert into books (title,author_id)\n"+
                        "values (?, ?)",Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, title);
                statement.setInt(2,authorId);
                statement.executeUpdate();

                set = statement.getGeneratedKeys();
                while (set.next()){
                    bookId = set.getInt(1);
                    System.out.println("Generated book_id: " + bookId);
                }

                c.commit();
            }catch (Exception e){
                e.printStackTrace();
                c.rollback();
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
