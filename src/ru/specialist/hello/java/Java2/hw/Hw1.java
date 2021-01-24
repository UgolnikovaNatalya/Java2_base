package ru.specialist.hello.java.Java2.hw;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Hw1 {

    private static final char LETTER_TO_COUNT = ',';

    static int getNumberOfLetters(List<String> strList, char ch, int from, int to) {
        int ret = 0;
        for (int i=from; i<to; i++ ) {
            for (char currChar:strList.get(i).toCharArray())
                if (currChar==ch) ret++;
        }
        return ret;
    }

    static int getNumberOfLettersMT(List<String> strList, char ch, int nTasks) throws ExecutionException, InterruptedException{
        ExecutorService pool = Executors.newCachedThreadPool();
        List<Future<Integer>> tasks = new ArrayList<>();
        int stepSize = strList.size()/nTasks;

        int r = strList.size() % nTasks;

        for (int i = 0; i < nTasks; i++) {
            int a = i*stepSize;
            int b = i*stepSize + stepSize + (i == nTasks - 1 ? r : 0);
            tasks.add(pool.submit(() -> getNumberOfLetters(strList, ch, a, b)));
        }
        pool.shutdown();

        int sum = 0;
        for (Future<Integer> task : tasks){
            sum += task.get();
        }

        return sum;
    }

    public static void main (String...args) throws IOException, ExecutionException, InterruptedException {
        List<String> lines = Files.readAllLines(Paths.get("sample16mb.txt"));
        System.out.println("number of lines loaded: "+lines.size());

        System.out.println("\n*** SINGLE THREADED METHOD ***");
        System.out.print("Letters '"+LETTER_TO_COUNT+"' found: ");
        long startTime = System.nanoTime();
        System.out.println(getNumberOfLetters(lines, LETTER_TO_COUNT, 0, lines.size()));
        long time=System.nanoTime()-startTime;
        System.out.println("Elapsed time [ms]: "+time/1000000.0);

        System.out.println("\n*** MULTI THREADED METHOD ***");
        System.out.print("Letters '"+LETTER_TO_COUNT+"' found: ");
        startTime = System.nanoTime();
        System.out.println(getNumberOfLettersMT(lines, LETTER_TO_COUNT, 4));
        time=System.nanoTime()-startTime;;
        System.out.println("Elapsed time [ms]: "+time/1000000.0);
    }
}
