package ru.specialist.hello.java.Java2.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TransactionExample {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String LOGIN = "postgres";
    private static final String PASSWORD = "UgolnikoVa77792";
    public static void main(String[] args) {

        try (Connection c = DriverManager.getConnection(URL, LOGIN, PASSWORD)){
            c.setAutoCommit(false);

            System.out.println(c.getTransactionIsolation());

            Statement statement = c.createStatement();
            try {
                statement.execute("insert into books (title, author_id) values ('AAA', 1)");
                statement.execute("insert into books (title, author_id) values ('BBB', 1)");
                statement.execute("insert into books (title, author_id) values ('CCC', 999)");
                c.commit();
            } catch (Exception e){
                e.printStackTrace();
                c.rollback();
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
