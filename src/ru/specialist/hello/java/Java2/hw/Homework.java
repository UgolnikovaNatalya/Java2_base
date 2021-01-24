package ru.specialist.hello.java.Java2.hw;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * в несколько потоков посчитать кол-во запятых
 */


public class Homework {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {

        List<String> lines = null;

        try {
            lines = Files.readAllLines(Paths.get("sample16mb.txt"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(countCommasMultiThread(lines, 4));

    }

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
        int stepSize = lines.size()/nTasks;
        int r = lines.size() % nTasks;

        for (int i = 0; i < nTasks; i++) {
            int a = i*stepSize;
            int b = i*stepSize + stepSize + (i == nTasks - 1 ? r : 0);
            tasks.add(pool.submit(()-> countCommas(lines.subList(a, b))));
        }
        pool.shutdown();

        int sum = 0;
        for (Future<Integer> task : tasks){
            sum += task.get();
        }

        return sum;
    }
}

