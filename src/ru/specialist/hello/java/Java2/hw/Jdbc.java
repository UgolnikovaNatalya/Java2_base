package ru.specialist.hello.java.Java2.hw;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.*;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Jdbc {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String LOGIN = "postgres";
    private static final String PASSWORD = "UgolnikoVa77792";

    private static final String METHOD_INSERT = "insert";
    private static final String METHOD_UPDATE = "update";
    private static final String METHOD_DELETE = "delete";
    private static final String METHOD_VIEW = "view";
    private static final String METHOD_EXIT = "exit";

    /**
     * Атомарная буленовская переменная. Оба потока могут работать с ней
     * Тут отвечает за закрытие программы.
     * AtomicBoolean prClose - отвечает за закрытие программы
     *
     */
    private static final AtomicBoolean prClose = new AtomicBoolean(false);

    private static final Object monitor = new Object();

    public static void main(String[] args) {

        String method;
        Thread t0  = new Thread(getRunnable("logHW.txt"));
        t0.start();


        try (Connection c = DriverManager.getConnection(URL, LOGIN, PASSWORD);
             Scanner scanner = new Scanner(System.in)) {

            while (!prClose.get()) {
                System.out.println("Введите метод для таблицы \"AUTHORS\": \"insert\" или \"update\" или \"delete\" или \"view\" или \"exit\"");
                method = scanner.nextLine();
                prClose.set(method.equals(METHOD_EXIT));

                switch (method) {
                    case METHOD_INSERT -> insert(c, scanner);
                    case METHOD_UPDATE -> update(c, scanner);
                    case METHOD_DELETE -> delete(c, scanner);
                    case METHOD_VIEW -> System.out.println(getTextTable());
                    case METHOD_EXIT -> System.out.println("Завершение работы!");
                    default -> System.err.println("Неизвестный метод!!!\"" + method + "\"");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insert( Connection c, Scanner scannerStr) {

        synchronized (monitor) {
            System.out.println("Введите имя автора:");
            String firstName = scannerStr.nextLine();

            System.out.println("Введите фамилию автора:");
            String lastName = scannerStr.nextLine();

            try {
                PreparedStatement statement = c.prepareStatement("insert into authors\n" +
                        "(author_name, last_name)\n" +
                        "values (?, ?)", Statement.RETURN_GENERATED_KEYS);

                statement.setString(1, firstName);
                statement.setString(2, lastName);

                statement.executeUpdate();
                ResultSet set = statement.getGeneratedKeys();
                while (set.next()) {
                    System.out.println("Добавлен автор, author_id: " + set.getInt(1));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //TODO: not found handling
    private static void delete(Connection c, Scanner scanner) {
        int countDeleteBooks;
        int countDeleteAuthors;

        try {
            synchronized (monitor) {
                System.out.println("Введите author_id для удаления строки:");
                int idAuthor = Integer.parseInt(scanner.nextLine());
                if(idAuthor > 0) {
                    try {
                        PreparedStatement statement = c.prepareStatement("delete from books\n" +
                                "where author_id = ?");
                        statement.setInt(1, idAuthor);
                        countDeleteBooks = statement.executeUpdate();

                        statement = c.prepareStatement("delete from authors\n" +
                                "where author_id = ?");

                        statement.setInt(1, idAuthor);
                        countDeleteAuthors = statement.executeUpdate();

                        System.out.println("author_id = " + idAuthor + ". Удалено авторов: " + countDeleteAuthors + ". Удалено книг: " + countDeleteBooks);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    System.err.println("author_id может быть только больше 0!");
                }
            }
        } catch (NumberFormatException e) {
            System.err.println("Введено не число: " + e.getMessage());
        }
    }


    //TODO: not found handling
    private static void update(Connection c, Scanner scanner) {
        try {
            synchronized (monitor) {
                System.out.println("Введите author_id для корректировки:");
                int idAuthor = Integer.parseInt(scanner.nextLine());
                if(idAuthor > 0) {
                    System.out.println("Введите имя автора:");
                    String firstName = scanner.nextLine();

                    System.out.println("Введите фамилию автора:");
                    String lastName = scanner.nextLine();

                    try {
                        PreparedStatement statement = c.prepareStatement("update authors set\n" +
                                "author_name = ?, last_name = ? \n" +
                                "where author_id = ?");
                        statement.setString(1, firstName);
                        statement.setString(2, lastName);
                        statement.setInt(3, idAuthor);
                        statement.executeUpdate();

                        System.out.println("Скорректирована строка author_id = " + idAuthor);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }else{
                    System.err.println("author_id может быть только больше 0!");
                }
            }
        } catch (NumberFormatException e) {
            System.err.println("Введено не число: " + e.getMessage());
        }
    }

    private static Runnable getRunnable(String fileName){
        return() ->{
            while (!prClose.get()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String textTable = getTextTable();
                addTextFile(fileName, textTable);
            }
        };
    }

    private static String getTextTable(){
        synchronized (monitor) {
            java.util.Date date = new Date();
            StringBuilder sb = new StringBuilder();
            sb.append("\n");
            sb.append(date.toString());
            sb.append("\n");
            String textFile = "";
            try (Connection c = DriverManager.getConnection(URL, LOGIN, PASSWORD)) {
                PreparedStatement statement = c.prepareStatement("select 'author_id = ' || author_id::varchar\n " +
                        "||'; author_name = ' ||author_name\n" +
                        "||'; last_name = ' ||last_name textRow\n" +
                        "from authors");

                ResultSet set = statement.executeQuery();
                while (set.next()) {
                    sb.append(set.getString("textRow"));
                    sb.append("\n");
                }
                textFile = sb.toString();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return textFile;
        }
    }

/*    private static void addTextFile(String fileName, String textTable){
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try(BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(fileName, true))) {
            bufferWriter.write(textTable);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    private static void addTextFile(String fileName, String textTable){
        Path path = Paths.get(fileName);
        try {
            if (!Files.exists(path))
                Files.createFile(path);
            Files.write(path, textTable.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
