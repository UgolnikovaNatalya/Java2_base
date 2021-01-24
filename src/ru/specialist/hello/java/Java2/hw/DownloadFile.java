package ru.specialist.hello.java.Java2.hw;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * Класс для скачивания файла из интернета.
 * Сделала сама из интернета. Для себя сделала
 */
public class DownloadFile {
//    public static final String DOCXURL = "https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1jinkiLKxF4g81k4WtcwNDKGTmxpjWLge";
//    public static final String DOCXPATH = "C://Users//" + System.getProperty("user.name") + "//Desktop//Ugolnikova//document.docx";

    public static final String TXTURL = "https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1LHWAJMcQiA2rTLUM6TiyYoNNhM9M2Cv4";
//    public static final String TXTPATH = "C://Users//" + System.getProperty("user.name") + "//Desktop//Ugolnikova//document.txt";
    //скачиваем файл в директорию проекта(для подсчета)
    public static final String TXTPATH = "document.txt";


    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        downloadDocx();
        downloadTxt();

        List<String> lines = null;

        try {
            lines = Files.readAllLines(Path.of(TXTPATH));

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(countCommasMultiThread(lines, 4));
    }

    private static void downloadTxt() {
        try {
            URL website = new URL(TXTURL);
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());

            FileOutputStream fos = new FileOutputStream(TXTPATH);

            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        } catch (Exception e) {
            System.out.println("Not downloaded!");
        }
    }

//    private static void downloadDocx() {
//        try {
//            URL website = new URL(DOCXURL);
//            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
//
//            FileOutputStream fos = new FileOutputStream(DOCXPATH);
//
//            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
//        } catch (Exception e) {
//            System.out.println("Not downloaded!");
//        }
//    }


    private static int countCommas(List<String> lines) {
        if (lines == null)
            return 0;

        int count = 0;
        for (String l : lines) {
            for (char c : l.toCharArray()) {
                if (c == ',')
                    count++;
            }
        }
        return count;
    }

    private static int countCommasMultiThread(List<String> lines, int nTasks) throws ExecutionException, InterruptedException {
        if (lines == null)
            return 0;

        if (lines.size() < nTasks)
            return countCommas(lines);

        ExecutorService pool = Executors.newCachedThreadPool();

        List<Future<Integer>> tasks = new ArrayList<>();
        int stepSize = lines.size() / nTasks;
        int r = lines.size() % nTasks;

        for (int i = 0; i < nTasks; i++) {
            int a = i * stepSize;
            int b = i * stepSize + stepSize + (i == nTasks - 1 ? r : 0);
            tasks.add(pool.submit(() -> countCommas(lines.subList(a, b))));
        }
        pool.shutdown();

        int sum = 0;
        for (Future<Integer> task : tasks) {
            sum += task.get();
        }

        return sum;

    }
}
