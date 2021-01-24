package ru.specialist.hello.java.Java2.labor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorLabVar2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        long t1 = System.currentTimeMillis();
        System.out.println(multiThread(100000,1));
        long t2 = System.currentTimeMillis();
        System.out.printf("Time (one Task): %d ms ", t2 -t1);

        t1 = System.currentTimeMillis();
        System.out.println(multiThread(100000,4));
        t2 = System.currentTimeMillis();
        System.out.printf("Time (multi Tasks): %d ms \n", t2 -t1);
//-------------------------------------------------------------
        t1 = System.currentTimeMillis();
        System.out.println(multiThread2(100000,1));
        t2 = System.currentTimeMillis();
        System.out.printf("Time (one Thread): %d ms \n", t2 -t1);

        t1 = System.currentTimeMillis();
        System.out.println(multiThread2(100000,4));
        t2 = System.currentTimeMillis();
        System.out.printf("Time (multi Threads): %d ms \n", t2 -t1);
    }

    /**
     * Метод подсчета в треде
     * @param from от минимального числа
     * @param to к максимальному в зависимости от шага
     * @return сумму в переделах шага
     */
    private static int sum (int from, int to){
        int sum = 0;
        for (int i = from; i <= to; i++) {
            sum += i;
        }return sum;
    }

    /**
     * Метод мультипоточного подсчета
     * @param to вводим число, которое нужно посчитать
     * @param nTasks в сколько задач решается
     * @return сумму потоков
     * @throws ExecutionException обработка ошибок
     * @throws InterruptedException обработка ошибок
     */
    private static int multiThread(int to, int nTasks) throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newCachedThreadPool();
        List<Future<Integer>> tasks = new ArrayList<>();
        int stepSize = to / nTasks;
        int r = to % nTasks;

        for (int i = 0; i < nTasks; i++) {
            int a = i * stepSize + 1;
            int b = i * stepSize + stepSize + (i== nTasks - 1 ? r : 0);
            tasks.add(pool.submit(()-> sum(a, b)));
        }
        pool.shutdown();
        int sum = 0;
        for (Future<Integer> task : tasks) {
            sum += task.get();
        } return sum;
    }

    private static int multiThread2(int to, int nTasks) throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(nTasks);
        List<Future<Integer>> tasks = new ArrayList<>();
        int stepSize = to / nTasks;
        int r = to % nTasks;

        for (int i = 0; i < nTasks; i++) {
            int a = i * stepSize + 1;
            int b = i * stepSize + stepSize + (i== nTasks - 1 ? r : 0);
            tasks.add(pool.submit(()-> sum(a, b)));
        }
        pool.shutdown();
        int sum = 0;
        for (Future<Integer> task : tasks) {
            sum += task.get();
        } return sum;
    }
}
